package edu.ucsb.mapache.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ucsb.mapache.entities.AppUser;
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

    private String jwtValue = "eyJhbGciOiJIUzI1NiJ9.eyJodHRwczovL3Byb2otbWFwYWNoZS1zZWFyY2guaGVyb2t1YXBwLmNvbSI6eyJlbWFpbCI6InBodGNvbkB1Y3NiLmVkdSIsImdpdmVuX25hbWUiOiJQaGlsbCIsImZhbWlseV9uYW1lIjoiQ29ucmFkIn0sImlzcyI6Imh0dHBzOi8vY3MxNTYtdzIxLXN0YWZmLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJnb29nbGUtb2F1dGgyfDExNTg1Njk0ODIzNDI5ODQ5MzQ5NiIsImF1ZCI6WyJodHRwczovL3Byb2otbWFwYWNoZS1zZWFyY2guaGVyb2t1YXBwLmNvbSIsImh0dHBzOi8vY3MxNTYtdzIxLXN0YWZmLnVzLmF1dGgwLmNvbS91c2VyaW5mbyJdLCJpYXQiOjE2MTYzNjk5ODIsImV4cCI6MTYxNjQ1NjM4MiwiYXpwIjoiVTFpclFoSHhjUnBnS1FKdVRNWjAyTXg5NFVLeUVDNU8iLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIn0.6nDBZ4Zr5OwYGpCMy1AjRvSqJB7aPfmBq4B3A34XQuE";
    private String authorization = "Bearer: " + jwtValue;
    private String email = "phtcon@ucsb.edu";
    private String namespace = "https://proj-mapache-search.herokuapp.com";

    private String authorizationWithNoCustomClaim = "Bearer: "
            + "eyJhbGciOiJIUzI1NiJ9.e30.ZRrHA1JJJW8opsbCGfG_HACGpVUMN_a9IV7pAx_Zmeo";

    @MockBean
    private CounterRepository counterRepository;

    @MockBean
    private AppUserRepository appUserRepository;

    @MockBean
    private SearchRepository searchRepository;

    @MockBean
    private NowService nowService;

    @MockBean
    private CounterService counterService;

    @Autowired
    private GoogleSearchService googleSearchService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(googleSearchService, "globalApiToken", "globalApiTokenValue");
        ReflectionTestUtils.setField(googleSearchService, "namespace", namespace);
    }

    @Test
    public void test_getJWT() {
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

        assertTrue(GoogleSearchService.shouldReset(lastUpdateTime, nowTime));
    }

    @Test
    public void test_shouldReset_false() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        java.util.Date lastUpdate = sdf.parse("06/24/2017 00:01");
        java.util.Date now = sdf.parse("06/24/2017 23:59");

        long lastUpdateTime = lastUpdate.getTime(); // ms since 1/1/1970 midnight GMT
        long nowTime = now.getTime(); // ms since 1/1/1970 midnight GMT

        assertFalse(GoogleSearchService.shouldReset(lastUpdateTime, nowTime));
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

    @Test
    public void test_getCurrentUser_inAppUsers() throws ParseException {
        AppUser appUser = new AppUser(0L, email, "Phill", "Conrad");
        List<AppUser> users = new ArrayList<>();
        users.add(appUser);

        when(appUserRepository.findByEmail(email)).thenReturn(users);

        AppUser result = googleSearchService.getCurrentUser(authorization);
        assertEquals(appUser, result);
        verify(appUserRepository, times(0)).save(appUser);

    }

    @Test
    public void test_getCurrentUser_notInAppUsers() throws ParseException {
        AppUser appUser = new AppUser(0L, email, "Phill", "Conrad");
        List<AppUser> emptyList = new ArrayList<>();

        when(appUserRepository.findByEmail(email)).thenReturn(emptyList);

        AppUser result = googleSearchService.getCurrentUser(authorization);
        assertEquals(appUser, result);
        verify(appUserRepository, times(1)).save(eq(appUser));

    }

    @Test
    public void test_getCurrentUser_customClaimMissing() throws ParseException {
        AppUser result = googleSearchService.getCurrentUser(authorizationWithNoCustomClaim);
        assertNull(result);
    }

    @Test
    public void test_decrementSearchesForUser_timeNotExpired_quotaNotExpired()
            throws GoogleSearchService.SearchQuotaExceededException, ParseException {

        AppUser appUser = new AppUser(0L, email, "Phill", "Conrad");
        appUser.setSearchRemain(GoogleSearchService.SEARCH_LIMIT);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        java.util.Date lastUpdate = sdf.parse("06/24/2017 00:01");
        java.util.Date now = sdf.parse("06/24/2017 23:59");

        appUser.setTime(lastUpdate.getTime());
        when(nowService.currentTime()).thenReturn(now.getTime());

        googleSearchService.decrementSearchesForUser(appUser);

        assertEquals(GoogleSearchService.SEARCH_LIMIT - 1, appUser.getSearchRemain());
        // assertEquals(now.getTime(), appUser.getTime());

        verify(appUserRepository, times(1)).save(eq(appUser));

    }

    @Test
    public void test_decrementSearchesForUser_timeNotExpired_quotaJustNowExpired()
            throws GoogleSearchService.SearchQuotaExceededException, ParseException {

        AppUser appUser = new AppUser(0L, email, "Phill", "Conrad");
        appUser.setSearchRemain(1);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        java.util.Date lastUpdate = sdf.parse("06/24/2017 00:01");
        java.util.Date now = sdf.parse("06/24/2017 23:59");

        appUser.setTime(lastUpdate.getTime());
        when(nowService.currentTime()).thenReturn(now.getTime());

        Assertions.assertThrows(GoogleSearchService.SearchQuotaExceededException.class, () -> {
            googleSearchService.decrementSearchesForUser(appUser);

        });

        assertEquals(0, appUser.getSearchRemain());
        verify(appUserRepository, times(1)).save(eq(appUser));

    }

    @Test
    public void test_decrementSearchesForUser_timeNotExpired_quotaPreviouslyExpired()
            throws GoogleSearchService.SearchQuotaExceededException, ParseException {

        AppUser appUser = new AppUser(0L, email, "Phill", "Conrad");
        appUser.setSearchRemain(0);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        java.util.Date lastUpdate = sdf.parse("06/24/2017 00:01");
        java.util.Date now = sdf.parse("06/24/2017 23:59");

        appUser.setTime(lastUpdate.getTime());
        when(nowService.currentTime()).thenReturn(now.getTime());

        Assertions.assertThrows(GoogleSearchService.SearchQuotaExceededException.class, () -> {
            googleSearchService.decrementSearchesForUser(appUser);

        });

        assertEquals(0, appUser.getSearchRemain());
        verify(appUserRepository, times(0)).save(eq(appUser));

    }

    @Test
    public void test_decrementSearchesForUser_timeExpired()
            throws GoogleSearchService.SearchQuotaExceededException, ParseException {

        AppUser appUser = new AppUser(0L, email, "Phill", "Conrad");
        appUser.setSearchRemain(GoogleSearchService.SEARCH_LIMIT);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        java.util.Date lastUpdate = sdf.parse("06/24/2017 12:34");
        java.util.Date now = sdf.parse("06/25/2017 12:35");

        appUser.setTime(lastUpdate.getTime());
        when(nowService.currentTime()).thenReturn(now.getTime());

        googleSearchService.decrementSearchesForUser(appUser);

        assertEquals(GoogleSearchService.SEARCH_LIMIT - 1, appUser.getSearchRemain());
        assertEquals(now.getTime(), appUser.getTime());

        verify(appUserRepository, times(1)).save(eq(appUser));

    }

   


}