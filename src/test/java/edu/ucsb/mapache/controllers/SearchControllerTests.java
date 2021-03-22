package edu.ucsb.mapache.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
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
import edu.ucsb.mapache.services.GoogleSearchService;

@WebMvcTest(value = SearchController.class)
@WithMockUser
public class SearchControllerTests {
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
    mockMvc
        .perform(
            get("/api/member/search/basic?searchQuery=github").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().is(401));
  }
  @Test
  public void test_quota_unauthorizedIfNotMember() throws Exception {
    mockMvc
        .perform(
            get("/api/member/search/quota").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().is(401));
  }


  @Test
  public void test_basicSearch_asMemberOrAdmin() throws Exception {
    AppUser appUser = getMockAppUser();
  
    when(googleSearchService.performSearch(any(String.class),any(String.class))).thenReturn("SampleResult");
    when(authControllerAdvice.getIsMemberOrAdmin(any(String.class))).thenReturn(true);
    when(googleSearchService.getCurrentUser(any(String.class))).thenReturn(appUser);
    MvcResult response = mockMvc
        .perform(
            get("/api/member/search/basic?searchQuery=github").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().isOk()).andReturn();

    String responseString = response.getResponse().getContentAsString();

    assertEquals("SampleResult", responseString);

  }

  @Test
  public void test_searchWithAPIToken() throws Exception {
      AppUser appUser = getMockAppUser();
      appUser.setApiToken("testTokenABC123");
      when(googleSearchService.performSearch(any(String.class),any(String.class))).thenReturn("SampleResult");
      when(authControllerAdvice.getIsMemberOrAdmin(any(String.class))).thenReturn(true);
      when(googleSearchService.getCurrentUser(any(String.class))).thenReturn(appUser);
      MvcResult response = mockMvc
          .perform(
              get("/api/member/search/basic?searchQuery=github").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
          .andExpect(status().isOk()).andReturn();

      String responseString = response.getResponse().getContentAsString();
      assertEquals("SampleResult", responseString);
  }

  @Test
  public void test_basicSearch_searchQuotaExceeded() throws Exception {
   
    when(authControllerAdvice.getIsMemberOrAdmin(any(String.class))).thenReturn(true);
    when(googleSearchService.performSearch(any(String.class),any(String.class))).thenThrow(new GoogleSearchService.SearchQuotaExceededException("error message"));
    MvcResult response = mockMvc
        .perform(
            get("/api/member/search/basic?searchQuery=github").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().isForbidden()).andReturn();
    String responseString = response.getResponse().getContentAsString();
    String expected="{\"error\":\"error message\"}";
    assertEquals(expected,responseString);
  }

  @Test
  public void test_Quota() throws Exception {
    AppUser appUser = getMockAppUser();
  
    when(googleSearchService.performSearch(any(String.class),any(String.class))).thenReturn("SampleResult");
    when(authControllerAdvice.getIsMemberOrAdmin(any(String.class))).thenReturn(true);
    when(googleSearchService.getCurrentUser(any(String.class))).thenReturn(appUser);
    MvcResult response = mockMvc
        .perform(
            get("/api/member/search/quota").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().isOk()).andReturn();
    Map<String, Object> sampleResponse = new HashMap<String, Object>();
    sampleResponse.put("quota", appUser.getSearchRemain());
    String expected = mapper.writeValueAsString(sampleResponse);
    String responseString = response.getResponse().getContentAsString();
    assertEquals(expected, responseString);

  }
}