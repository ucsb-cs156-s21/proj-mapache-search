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

/**
 * Service object that wraps the Google Custom Search API
 */
@Service
public class GoogleSearchServiceHelper {

    private Logger logger = LoggerFactory.getLogger(GoogleSearchServiceHelper.class);
    public final static String SEARCH_ID = "001539284272632380888:kn5n6ubsr7x";
    public final static int SEARCH_LIMIT = 100;

    private final RestTemplate restTemplate;

    @Value("${app.google.search.apiToken}")
    private String globalApiToken;

    // We can't have OAuth2AuthenticationToken token as argument, otherwise it'll
    // give us instantiate bean error
    public GoogleSearchServiceHelper(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    // This is the full format of the google api endpoint!
    // private static final String SEARCH_ENDPOINT =
    // "https://www.googleapis.com/customsearch/v1?key={key}&cx={searchId}&q={query}&alt={outputFormat}&start={start}&siteSearch={siteRestrict}&dateRestrict={dateRestrict}";
    public static final String SEARCH_ENDPOINT = "https://www.googleapis.com/customsearch/v1?key={key}&cx={searchId}&q={query}&alt={outputFormat}";

    public String getJSON(SearchParameters params, String apiKey) throws HttpClientErrorException {
        logger.info("apiKey=" + apiKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        Map<String, String> uriVariables = Map.of("key", apiKey, "searchId", SEARCH_ID, "query", params.getQuery(),
                "outputFormat", "json");

        ResponseEntity<String> re = restTemplate.exchange(SEARCH_ENDPOINT, HttpMethod.GET, entity, String.class,
                uriVariables);
        return re.getBody();
    }

}