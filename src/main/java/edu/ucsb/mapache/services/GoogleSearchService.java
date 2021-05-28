package edu.ucsb.mapache.services;

import org.springframework.stereotype.Service;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
import edu.ucsb.mapache.repositories.UserSearchRepository;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    public final static String SEARCH_ID = "001539284272632380888:kn5n6ubsr7x";
    public final static int SEARCH_LIMIT = 100;

    @Autowired
    private NowService nowService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Value("${app.google.search.apiToken}")
    private String globalApiToken;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private CounterService counterService;

    @Autowired
    GoogleSearchServiceHelper googleSearchServiceHelper;

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
    public static final String SEARCH_ENDPOINT = "https://www.googleapis.com/customsearch/v1?key={key}&cx={searchId}&q={query}&alt={outputFormat}";

    public String performSearch(String searchQuery, String authorization) throws SearchQuotaExceededException {

        AppUser you = getCurrentUser(authorization);
        String apiToken = globalApiToken;
        String userToken = you.getApiToken();
        String result = null;

        if (isValidApiToken(userToken)) {
            apiToken = userToken;
            decrementSearchesForUser(you);

        } else {
            decrementGlobalApiSearches();
        }

        SearchParameters sp = new SearchParameters();
        sp.setQuery(searchQuery);
        sp.setPage(1);
        logger.info("sp={} apiToken={}", sp, apiToken);
        try {
            result = googleSearchServiceHelper.getJSON(sp, userToken);
        } catch (HttpClientErrorException e) {
            return "{\"error\": \"401: Unauthorized\"}";
        }

        saveToSearchRepository(searchQuery);
        saveToUserSearchRepository(searchQuery,authorization); 

        logger.info("result={}", result);
        return result;
    }

    public static boolean isValidApiToken(String token) {
        return (token != null && !(token.equals("")) && !(token.equals("invalid token")));
    }

    public void decrementSearchesForUser(AppUser you) throws SearchQuotaExceededException {

        long lastUpdate = you.getTime();
        long currentTime = nowService.currentTime();
        if (shouldReset(lastUpdate, currentTime)) {
            you.setSearchRemain(SEARCH_LIMIT);
            you.setTime(currentTime);
        }
        int searchesRemaining = you.getSearchRemain();
        if (searchesRemaining > 0) {
            you.decrSearchRemain();
            appUserRepository.save(you);
        }
        if (you.getSearchRemain() <= 0) {
            throw new SearchQuotaExceededException("Quota exceeded for user");
        }
    }

    public void decrementGlobalApiSearches() throws SearchQuotaExceededException {
        String key = "globalGoogleSearchApiTokenUsesToday";
        Counter c = counterService.resetOrDecrement(key, 100, 24);
        if (c.getValue() <= 0) {
            throw new SearchQuotaExceededException("Global quota exceeded");
        }
    }

    public void saveToSearchRepository(String searchQuery) {
        Search s;
        if (!searchRepository.findBySearchTerm(searchQuery).isEmpty()) {
            s = searchRepository.findBySearchTerm(searchQuery).get(0);
            s.setCount(s.getCount() + 1);
        } else {
            s = new Search();
            s.setSearchTerm(searchQuery);
            s.setCount(1);
        }
        searchRepository.save(s);
    }

    public void saveToUserSearchRepository(String searchQuery,String authorization){
	    UserSearch s;
	    
	     DecodedJWT jwt = getJWT(authorization);
        Map<String, Object> customClaims = jwt.getClaim(namespace).asMap();
        if (customClaims == null)
            return;
	s=new UserSearch();
	String firstName = (String) customClaims.get("given_name");
        String lastName = (String) customClaims.get("family_name");
        s.setSearchTerm(searchQuery);
	s.setUserID(firstName+" "+lastName);
	String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z").format(Calendar.getInstance().getTime());
        s.setTimestamp(timestamp);

	UserSearchRepository.save(s);

    }



    public AppUser getCurrentUser(String authorization) {
        DecodedJWT jwt = getJWT(authorization);
        Map<String, Object> customClaims = jwt.getClaim(namespace).asMap();
        if (customClaims == null)
            return null;
        String email = (String) customClaims.get("email");
        List<AppUser> users = appUserRepository.findByEmail(email);// obtain email of current user
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
        long msPerDay = 24 * 60 * 60 * 1000;
        return (currentTime - lastUpdate) > msPerDay;
    }

}
