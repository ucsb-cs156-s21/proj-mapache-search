package edu.ucsb.mapache.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.http.MediaType;

import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.entities.AppUser;
import edu.ucsb.mapache.entities.Search;
import edu.ucsb.mapache.models.SearchParameters;
import edu.ucsb.mapache.repositories.AppUserRepository;
import edu.ucsb.mapache.repositories.SearchRepository;
import edu.ucsb.mapache.services.GoogleSearchService;
import edu.ucsb.mapache.services.SearchSupportService;

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
  private SearchSupportService searchSupportService;
  @MockBean
  private SearchRepository searchRepository;

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
  public void test_basicSearch() throws Exception {
    AppUser appUser = getAppUser();
  
    when(googleSearchService.getJSON(any(SearchParameters.class),any(String.class))).thenReturn("SampleResult");
    when(authControllerAdvice.getIsMember(any(String.class))).thenReturn(true);
    when(searchSupportService.getCurrentUser(any(String.class))).thenReturn(appUser);
    MvcResult response = mockMvc
        .perform(
            get("/api/member/search/basic?searchQuery=github").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().isOk()).andReturn();

    String responseString = response.getResponse().getContentAsString();

    assertEquals("SampleResult", responseString);

  }

  @Test
  public void test_searchWithAPIToken() throws Exception {
      AppUser appUser = getAppUser();
      appUser.setApiToken("testTokenABC123");
      when(googleSearchService.getJSON(any(SearchParameters.class),eq("testTokenABC123"))).thenReturn("SampleResult");
      when(authControllerAdvice.getIsMember(any(String.class))).thenReturn(true);
      when(searchSupportService.getCurrentUser(any(String.class))).thenReturn(appUser);
      MvcResult response = mockMvc
          .perform(
              get("/api/member/search/basic?searchQuery=github").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
          .andExpect(status().isOk()).andReturn();

      String responseString = response.getResponse().getContentAsString();
      assertEquals("SampleResult", responseString);
  }


  @Test
  public void test_basicSearch_searchCount_new_searchTerm() throws Exception {
    AppUser appUser = getAppUser();
    List<Search> searches = new ArrayList<Search>();

    when(googleSearchService.getJSON(any(SearchParameters.class),any(String.class))).thenReturn("SampleResult");
    when(authControllerAdvice.getIsMember(any(String.class))).thenReturn(true);
    when(searchSupportService.getCurrentUser(any(String.class))).thenReturn(appUser);
    when(searchRepository.findBySearchTerm("github")).thenReturn(searches);

    MvcResult response = mockMvc
        .perform(
            get("/api/member/search/basic?searchQuery=github").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().isOk()).andReturn();

    String responseString = response.getResponse().getContentAsString();

    assertEquals("SampleResult", responseString);
    Search s2 = new Search();
    s2.setCount(1);
    s2.setSearchTerm("github");
    verify(searchRepository, times(1)).save(s2);
  }


  @Test
  public void test_basicSearch_searchCount_existing_searchTerm() throws Exception {
    AppUser appUser = getAppUser();
    Search s = new Search();
    s.setCount(1);
    s.setSearchTerm("github");
    List<Search> searches = new ArrayList<Search>();
    searches.add(s);
    when(googleSearchService.getJSON(any(SearchParameters.class),any(String.class))).thenReturn("SampleResult");
    when(authControllerAdvice.getIsMember(any(String.class))).thenReturn(true);
    when(searchSupportService.getCurrentUser(any(String.class))).thenReturn(appUser);
    when(searchRepository.findBySearchTerm("github")).thenReturn(searches);

    MvcResult response = mockMvc
        .perform(
            get("/api/member/search/basic?searchQuery=github").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().isOk()).andReturn();

    String responseString = response.getResponse().getContentAsString();

    assertEquals("SampleResult", responseString);
    Search s2 = new Search();
    s2.setCount(2);
    s2.setSearchTerm("github");
    verify(searchRepository, times(1)).save(s2);
  }

  @Test
  public void test_basicSearch_searchQuotaExceeded() throws Exception {
    AppUser appUser = getAppUser();
    appUser.setSearchRemain(0);
    when(googleSearchService.getJSON(any(SearchParameters.class),any(String.class))).thenReturn("SampleResult");
    when(authControllerAdvice.getIsMember(any(String.class))).thenReturn(true);
    when(searchSupportService.getCurrentUser(any(String.class))).thenReturn(appUser);
    MvcResult response = mockMvc
        .perform(
            get("/api/member/search/basic?searchQuery=github").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().isForbidden()).andReturn();

  }

  @Test
  public void test_basicSearch_shouldReset() throws Exception {
    AppUser appUser = getAppUser();
    when(googleSearchService.getJSON(any(SearchParameters.class),any(String.class))).thenReturn("SampleResult");
    when(authControllerAdvice.getIsMember(any(String.class))).thenReturn(true);
    when(searchSupportService.getCurrentUser(any(String.class))).thenReturn(appUser);
    when(searchSupportService.shouldReset(anyLong(),anyLong())).thenReturn(true);
    MvcResult response = mockMvc
        .perform(
            get("/api/member/search/basic?searchQuery=github").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().isOk()).andReturn();

    String responseString = response.getResponse().getContentAsString();

    assertEquals("SampleResult", responseString);

  }
  @Test
  public void test_Quota() throws Exception {
    AppUser appUser = getAppUser();
  
    when(googleSearchService.getJSON(any(SearchParameters.class),any(String.class))).thenReturn("SampleResult");
    when(authControllerAdvice.getIsMember(any(String.class))).thenReturn(true);
    when(searchSupportService.getCurrentUser(any(String.class))).thenReturn(appUser);
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

  private AppUser getAppUser() {
    AppUser appUser = new AppUser();
    appUser.setId(1);
    appUser.setEmail("haixinlin123@umail.ucsb.edu");
    appUser.setFirstName("Hunter");
    appUser.setLastName("Lin");
    appUser.setSearchRemain(100);
    appUser.setTime(0);
    return appUser;
  }

}