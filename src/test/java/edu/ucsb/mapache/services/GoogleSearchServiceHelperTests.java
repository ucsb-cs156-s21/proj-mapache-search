package edu.ucsb.mapache.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import edu.ucsb.mapache.models.SearchParameters;
import edu.ucsb.mapache.repositories.AppUserRepository;
import edu.ucsb.mapache.repositories.CounterRepository;
import edu.ucsb.mapache.repositories.SearchRepository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestClientTest(GoogleSearchServiceHelper.class)
public class GoogleSearchServiceHelperTests {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private GoogleSearchServiceHelper googleSearchServiceHelper;

    @Test
    public void test_getJSON() {

        String apiToken="fake-api-token";
        String searchQuery="spring boot";
        String searchQueryEncoded="spring%20boot";

        SearchParameters sp = new SearchParameters();
        sp.setQuery(searchQuery);
        sp.setPage(1);
       
        String expectedUri = "https://www.googleapis.com/customsearch/v1?key=" + apiToken + "&cx=" + GoogleSearchService.SEARCH_ID + "&q=" + searchQueryEncoded + "&alt=json";

        String expectedJSON = "{}";
        this.mockRestServiceServer.expect(requestTo(expectedUri))
                .andRespond(withSuccess(expectedJSON, MediaType.APPLICATION_JSON));

        String actual = googleSearchServiceHelper.getJSON(sp, apiToken);
        assertEquals(expectedJSON,actual);
    }
}
