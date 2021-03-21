package edu.ucsb.mapache.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ucsb.mapache.entities.Search;
import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.repositories.AppUserRepository;
import edu.ucsb.mapache.repositories.CounterRepository;
import edu.ucsb.mapache.repositories.SearchRepository;
import edu.ucsb.mapache.repositories.StudentRepository;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoogleSearchServiceTests {

    @MockBean
    private CounterRepository counterRepository;

    @MockBean
    private AppUserRepository appUserRepository;

    @MockBean
    private SearchRepository searchRepository;

    @Autowired
    private GoogleSearchService googleSearchService;

    @Test
    public void test_getJWT() {
        String authorization = "Bearer: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lc3BhY2UiOnsiZW1haWwiOiJ0ZXN0QGdtYWlsLmNvbSJ9LCJzdWIiOiIxMjM0NTYiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.6kcYK01GnVjCMELcgUyFJBYe1DeQ9y4NngDgYdwYwqE";

        DecodedJWT actual = googleSearchService.getJWT(authorization);
        // Writing the code twice is not the best way to do a test.
        // In this case, I don't yet have a better alterantive to propose.
        DecodedJWT expected = JWT.decode(authorization.substring(7));
        // Note that the .equals for DecodedJWT is broken, so we need this instead:
        assertEquals(expected.getToken(), actual.getToken());    
    }

    @Test
    public void test_shouldReset_true() throws ParseException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        java.util.Date lastUpdate = sdf.parse("06/24/2017");
        java.util.Date now = sdf.parse("06/26/2017");
        
        long lastUpdateTime = lastUpdate.getTime(); // ms since 1/1/1970 midnight GMT
        long nowTime = now.getTime(); // ms since 1/1/1970 midnight GMT

        assertTrue( GoogleSearchService.shouldReset(lastUpdateTime, nowTime));
    }

    @Test
    public void test_shouldReset_false() throws ParseException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        java.util.Date lastUpdate = sdf.parse("06/24/2017 00:01");
        java.util.Date now = sdf.parse("06/24/2017 23:59");
        
        long lastUpdateTime = lastUpdate.getTime(); // ms since 1/1/1970 midnight GMT
        long nowTime = now.getTime(); // ms since 1/1/1970 midnight GMT

        assertFalse( GoogleSearchService.shouldReset(lastUpdateTime, nowTime));
    }

    @Test
    public void test_saveToSearchRepository_firstTime() throws ParseException {
        String searchQuery = "spring boot testing mocking repositories";
        List<Search> searches = new ArrayList<Search>();
        when(searchRepository.findBySearchTerm(searchQuery)).thenReturn(searches);

        googleSearchService.saveToSearchRepository(searchQuery);

        Search s = new Search();
        s.setCount(1);
        s.setSearchTerm(searchQuery);
        verify(searchRepository, times(1)).save(s); 
    }

    @Test
    public void test_saveToSearchRepository_update() throws ParseException {
        String searchQuery = "spring boot testing mocking repositories";
        List<Search> searches = new ArrayList<Search>();
        Search before = new Search();
        before.setCount(5);
        before.setSearchTerm(searchQuery);
        searches.add(before);

        when(searchRepository.findBySearchTerm(searchQuery)).thenReturn(searches);

        googleSearchService.saveToSearchRepository(searchQuery);

        Search after = new Search();
        after.setCount(6);
        after.setSearchTerm(searchQuery);
        verify(searchRepository, times(1)).save(after); 
    }

}