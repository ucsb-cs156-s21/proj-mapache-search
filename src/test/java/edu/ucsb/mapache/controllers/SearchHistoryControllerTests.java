package edu.ucsb.mapache.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.auth0.jwt.JWT;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;

import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.entities.AppUser;
import edu.ucsb.mapache.entities.Search;
import edu.ucsb.mapache.entities.UserSearch;
import edu.ucsb.mapache.models.SearchParameters;
import edu.ucsb.mapache.repositories.AppUserRepository;
import edu.ucsb.mapache.repositories.SearchRepository;
import edu.ucsb.mapache.repositories.UserSearchRepository;
import edu.ucsb.mapache.services.GoogleSearchService;
import edu.ucsb.mapache.services.PropertiesService;

@WebMvcTest(value = SearchHistoryController.class)
@WithMockUser
public class SearchHistoryControllerTests {
  private String exampleAuthToken = "Bearer blah";
  private String jwtValue = "eyJhbGciOiJIUzI1NiJ9.eyJodHRwczovL3Byb2otbWFwYWNoZS1zZWFyY2guaGVyb2t1YXBwLmNvbSI6eyJlbWFpbCI6InBodGNvbkB1Y3NiLmVkdSIsImdpdmVuX25hbWUiOiJQaGlsbCIsImZhbWlseV9uYW1lIjoiQ29ucmFkIn0sImlzcyI6Imh0dHBzOi8vY3MxNTYtdzIxLXN0YWZmLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJnb29nbGUtb2F1dGgyfDExNTg1Njk0ODIzNDI5ODQ5MzQ5NiIsImF1ZCI6WyJodHRwczovL3Byb2otbWFwYWNoZS1zZWFyY2guaGVyb2t1YXBwLmNvbSIsImh0dHBzOi8vY3MxNTYtdzIxLXN0YWZmLnVzLmF1dGgwLmNvbS91c2VyaW5mbyJdLCJpYXQiOjE2MTYzNjk5ODIsImV4cCI6MTYxNjQ1NjM4MiwiYXpwIjoiVTFpclFoSHhjUnBnS1FKdVRNWjAyTXg5NFVLeUVDNU8iLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIn0.6nDBZ4Zr5OwYGpCMy1AjRvSqJB7aPfmBq4B3A34XQuE";
  private String authorization = "Bearer: " + jwtValue;
  private ObjectMapper mapper = new ObjectMapper();
  @Autowired
  private MockMvc mockMvc;
   
  @Autowired
  private SearchHistoryController searchhistorycontoller;
  
  @Autowired
  private ObjectMapper objectMapper;
  
  @MockBean
  AppUserRepository appUserRepository;
  @MockBean
  GoogleSearchService googleSearchService;
  @MockBean
  AuthControllerAdvice authControllerAdvice;

  @MockBean
  private SearchRepository searchRepository;

  @MockBean
  private UserSearchRepository userSearchRepository;

  @MockBean
  private PropertiesService propertiesService;

  private AppUser getMockAppUser() {
    AppUser appUser = new AppUser();
    appUser.setId(1L);
    appUser.setEmail("haixinlin123@umail.ucsb.edu");
    appUser.setFirstName("Hunter");
    appUser.setLastName("Lin");
    appUser.setSearchRemain(100);
    appUser.setTime(0);
    appUser.setApiToken("fake-but-valid-api-token");
    return appUser;
  }

  @Test
  public void test_getJWT() {
        DecodedJWT actual = searchhistorycontoller.getJWT(authorization);
        // Writing the code twice is not the best way to do a test.
        // In this case, I don't yet have a better alterantive to propose.
        DecodedJWT expected = JWT.decode(authorization.substring(7));
        // Note that the .equals for DecodedJWT is broken, so we need this instead:
        assertEquals(expected.getToken(), actual.getToken());
    }
  
  @Test
  public void test_basicSearch_unauthorizedIfNotMember() throws Exception {
    when(propertiesService.getNamespace()).thenReturn("https://proj-mapache-search.herokuapp.com");
    mockMvc.perform(get("/api/members/searchhistory/allusersearches").contentType("application/json")
        .header(HttpHeaders.AUTHORIZATION, exampleAuthToken)).andExpect(status().is(401));
  }

  @Test
  public void test_quota_unauthorizedIfNotMember() throws Exception {

    // TODO CHANGE TO APPROPRIATE URL FOR THIS CONTROLLER

    // mockMvc
    // .perform(
    // get("/api/member/search/quota").contentType("application/json").header(HttpHeaders.AUTHORIZATION,
    // exampleAuthToken))
    // .andExpect(status().is(401));
  }

  @Test
  public void test_getSearches_case1() throws Exception {

    // write code to test the method
    //
    // @GetMapping("/allusersearches")
    // public ResponseEntity<String> getSearches(@RequestHeader("Authorization")
    // String authorization)
    
    List<UserSearch> fakeUserSearchData = new ArrayList<UserSearch>();
    UserSearch usersearch = new UserSearch();
    long num=1L;
    usersearch.setId(num);
    usersearch.setUserID("PhillipConrad");
    usersearch.setSearchTerm("Phill Conrad");
    usersearch.setTimestamp("2021-05-29 13:40:10.561 +0000");
    fakeUserSearchData.add(usersearch);
    
    when(authControllerAdvice.getIsMember(any(String.class))).thenReturn(true);
    when(userSearchRepository.findByUserID("PhillipConrad")).thenReturn(fakeUserSearchData);
  
   
    MvcResult response = mockMvc
        .perform(
            get("/api/members/searchhistory/allusersearches").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().isOk()).andReturn();
    
    String expected = mapper.writeValueAsString(fakeSearchData);
    String responseString = response.getResponse().getContentAsString();
  

    assertEquals(expected, responseString);

    // I wrote 'case 1' because you'll probably need a few cases, e.g.
    // for not logged in, logged in as admin, logged in as member, etc.
    // depending on what the code is checking for
  }

  @Test
  public void test_getSearches_case2() throws Exception {

    // write code to test the method
    //
    // @GetMapping("/allusersearches")
    // public ResponseEntity<String> getSearches(@RequestHeader("Authorization")
    // String authorization)

    // I wrote 'case 2' because you'll probably need a few cases, e.g.
    // for not logged in, logged in as admin, logged in as member, etc.
    // depending on what the code is checking for
  }

  // ETC... until you've tested all of the cases for all of the methods.
  // Look in other controller tests for examples

}
