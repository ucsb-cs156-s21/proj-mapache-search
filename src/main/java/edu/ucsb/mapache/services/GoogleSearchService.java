package edu.ucsb.mapache.services;

import org.springframework.stereotype.Service;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import edu.ucsb.mapache.entities.AppUser;
import edu.ucsb.mapache.entities.Counter;
import edu.ucsb.mapache.entities.Search;
import edu.ucsb.mapache.models.SearchParameters;
import edu.ucsb.mapache.repositories.AppUserRepository;
import edu.ucsb.mapache.repositories.CounterRepository;
import edu.ucsb.mapache.repositories.SearchRepository;

/**
 * Service object that wraps the Google Custom Search API
 */
@Service
public class GoogleSearchService {

    public static class SearchQuotaExceededException extends Exception {
        private static final long serialVersionUID = 1L;
        public SearchQuotaExceededException(String msg) {
            super(msg);
        }
    }

    private Logger logger = LoggerFactory.getLogger(GoogleSearchService.class);
    private String searchId = "001539284272632380888:kn5n6ubsr7x";
    public final static int SEARCH_LIMIT = 100;


    @Autowired
    private AppUserRepository appUserRepository;

    @Value("${app.google.search.apiToken}")
    private String globalApiToken;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private CounterRepository counterRepository;

    @Value("${app.namespace}")
    private String namespace;

    public DecodedJWT getJWT(String authorization) {
        return JWT.decode(authorization.substring(7));
    }

    // We can't have OAuth2AuthenticationToken token as argument, otherwise it'll
    // give us instantiate bean error
    public GoogleSearchService() {
    }

    // This is the full format of the google api endpoint!
    // private static final String SEARCH_ENDPOINT =
    // "https://www.googleapis.com/customsearch/v1?key={key}&cx={searchId}&q={query}&alt={outputFormat}&start={start}&siteSearch={siteRestrict}&dateRestrict={dateRestrict}";
    private static final String SEARCH_ENDPOINT = "https://www.googleapis.com/customsearch/v1?key={key}&cx={searchId}&q={query}&alt={outputFormat}";

    public String getJSON(SearchParameters params, String apiKey) {
        logger.info("apiKey=" + apiKey);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        int startIndex = ((params.getPage() - 1) * 10) + 1;

        Map<String, String> uriVariables = Map.of("key", apiKey, "searchId", searchId, "query", params.getQuery(),
                "outputFormat", "json");

        String retVal = "";
        try {
            ResponseEntity<String> re = restTemplate.exchange(SEARCH_ENDPOINT, HttpMethod.GET, entity, String.class,
                    uriVariables);
            retVal = re.getBody();
        } catch (HttpClientErrorException e) {
            retVal = "{\"error\": \"401: Unauthorized\"}";
        }
        logger.info("from GoogleSearchService.getJSON: " + retVal);
        return retVal;
    }

    public String performSearch(String searchQuery, String authorization) 
    throws SearchQuotaExceededException {

        AppUser you = getCurrentUser(authorization);
        String apiToken = globalApiToken;
        String userToken = you.getApiToken();
        String result = null;

        if ( userToken==null || userToken.equals("") || userToken.equals("invalid token")) {            
            decrementGlobalApiSearches();
        } else {
            apiToken = userToken;
            decrementSearchesForUser(you); 
        }
         
        SearchParameters sp = new SearchParameters();
        sp.setQuery(searchQuery);
        sp.setPage(1);
        logger.info("sp={} apiToken={}", sp, apiToken);
        result = getJSON(sp, userToken);

        saveToSearchRepository(searchQuery);

        logger.info("result={}", result);
        return result;
    }

    public int remainingSearches(AppUser you) {
        return you.getSearchRemain();
    }

    public void decrementSearchesForUser(AppUser you) throws SearchQuotaExceededException {

        long lastUpdate = you.getTime();
        long currentTime = (long) (new java.util.Date().getTime());
        if ( shouldReset(lastUpdate, currentTime)) {
            you.setSearchRemain(SEARCH_LIMIT);
            you.setTime(currentTime);
        }
        you.decrSearchRemain();
        if (remainingSearches(you) <= 0) {
            throw new SearchQuotaExceededException("Quota exceeded for user"); 
        }
        appUserRepository.save(you);
    }

    public void decrementGlobalApiSearches() throws SearchQuotaExceededException {
        String key = "globalGoogleSearchApiTokenUsesToday";
        Counter c = Counter.resetOrDecrement(counterRepository, key, 100, 24);
        if (c.getValue()<=0) {
            throw new SearchQuotaExceededException("Global quota exceeded");
        }
    }


    public void saveToSearchRepository(String searchQuery) {
        Search s;
        if (!searchRepository.findBySearchTerm(searchQuery).isEmpty()) {
            s = searchRepository.findBySearchTerm(searchQuery).get(0);
            s.setCount(s.getCount() + 1);
            searchRepository.save(s);
        } else {
            s = new Search();
            s.setSearchTerm(searchQuery);
            s.setCount(1);
        }
        searchRepository.save(s);
    }

    public AppUser getCurrentUser(String authorization) {
        DecodedJWT jwt = getJWT(authorization);
        Map<String, Object> customClaims = jwt.getClaim(namespace).asMap();
        String email = (String) customClaims.get("email");
        logger.info("email={}", email);
        List<AppUser> users = appUserRepository.findByEmail(email);// obtain email of current user
        logger.info("user={}", users);
        AppUser you = null;
        if (users.isEmpty()) {
            you = new AppUser();
            you.setEmail(email);
            String firstName = (String) customClaims.get("given_name");
            String lastName = (String) customClaims.get("family_name");
            you.setFirstName(firstName);
            you.setLastName(lastName);

            appUserRepository.save(you);
        } else {
            you = users.get(0);
        }
        return you;
    }

    public static boolean shouldReset(long lastUpdate, long currentTime) {
        long lastUpdateDate = (lastUpdate - 8 * 60 * 60 * 1000) / (24 * 60 * 60 * 1000);
        long currentDate = (currentTime - 8 * 60 * 60 * 1000) / (24 * 60 * 60 * 1000);
        return currentDate > lastUpdateDate;
    }

}