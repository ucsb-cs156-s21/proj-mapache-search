package edu.ucsb.mapache.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

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
  //JWTCreator.Builder withClaim​(String name, String value)
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
        DecodedJWT expected = JWT.decode(authorization.substring(7));
        assertEquals(expected.getToken(), actual.getToken());
    }
  
  @Test
  public void test_basicSearch_unauthorizedIfNotMember() throws Exception {
    when(propertiesService.getNamespace()).thenReturn("https://proj-mapache-search.herokuapp.com");
    mockMvc.perform(get("/api/members/searchhistory/allusersearches").contentType("application/json")
        .header(HttpHeaders.AUTHORIZATION, exampleAuthToken)).andExpect(status().is(401));
  }

  @Test
  public void test_getSearches_ifisonlymember() throws Exception {
    
    List<UserSearch> fakeUserSearchData = new ArrayList<UserSearch>();
    UserSearch usersearch = new UserSearch();
    long num=1L;
    usersearch.setId(num);
    usersearch.setUserID("PhillipConrad");
    usersearch.setSearchTerm("Phill Conrad");
    usersearch.setTimestamp("2021-05-29 13:40:10.561 +0000");
    usersearch.setEmail("cool@google.com");
    fakeUserSearchData.add(usersearch);
    
    when(authControllerAdvice.getIsMember(any(String.class))).thenReturn(true);
    when(userSearchRepository.findByEmail(any(String.class))).thenReturn(fakeUserSearchData);
    when(propertiesService.getNamespace()).thenReturn("https://proj-mapache-search.herokuapp.com");
   
    MvcResult response = mockMvc
        .perform(
            get("/api/members/searchhistory/allusersearches").contentType("application/json").header(HttpHeaders.AUTHORIZATION, authorization))
        .andExpect(status().isOk()).andReturn();
    
    String expected = mapper.writeValueAsString(fakeUserSearchData);
    String responseString = response.getResponse().getContentAsString();
  

    assertEquals(expected, responseString);
  }

  @Test
  public void test_getSearches_ifisadmin() throws Exception {

    List<UserSearch> fakeUserSearchData = new ArrayList<UserSearch>();
    UserSearch usersearch = new UserSearch();
    long num=1L;
    usersearch.setId(num);
    usersearch.setUserID("PhillipConrad");
    usersearch.setSearchTerm("Phill Conrad");
    usersearch.setTimestamp("2021-05-29 13:40:10.561 +0000");
    usersearch.setEmail("email");
    fakeUserSearchData.add(usersearch);
    
    when(authControllerAdvice.getIsMember(any(String.class))).thenReturn(true);
    when(authControllerAdvice.getIsAdmin(any(String.class))).thenReturn(true);
    when(userSearchRepository.findAll()).thenReturn(fakeUserSearchData);
    when(propertiesService.getNamespace()).thenReturn("https://proj-mapache-search.herokuapp.com");
   
    MvcResult response = mockMvc
        .perform(
            get("/api/members/searchhistory/allusersearches").contentType("application/json").header(HttpHeaders.AUTHORIZATION, authorization))
        .andExpect(status().isOk()).andReturn();
    
    String expected = mapper.writeValueAsString(fakeUserSearchData);
    String responseString = response.getResponse().getContentAsString();
  

    assertEquals(expected, responseString);
}
}
