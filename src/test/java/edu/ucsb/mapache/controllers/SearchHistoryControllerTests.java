package edu.ucsb.mapache.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
  private ObjectMapper mapper = new ObjectMapper();
  @Autowired
  private MockMvc mockMvc;
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
    appUser.setId(1);
    appUser.setEmail("haixinlin123@umail.ucsb.edu");
    appUser.setFirstName("Hunter");
    appUser.setLastName("Lin");
    appUser.setSearchRemain(100);
    appUser.setTime(0);
    appUser.setApiToken("fake-but-valid-api-token");
    return appUser;
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
    //hello
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