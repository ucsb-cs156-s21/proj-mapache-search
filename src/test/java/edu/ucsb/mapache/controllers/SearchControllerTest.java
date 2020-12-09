//1607500799999 and  1607500800001 reset
//1607500800001 and 1607587199999 not reset
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.documents.SlackUser;
import edu.ucsb.mapache.entities.AppUser;
import edu.ucsb.mapache.repositories.SlackUserRepository;
import edu.ucsb.mapache.repositories.AppUserRepository;
import edu.ucsb.mapache.services.GoogleSearchService;

@WebMvcTest(value = SearchController.class)
@WithMockUser
public class SearchControllerTest {
  private String exampleAuthToken = "Bearer blah";
  private ObjectMapper mapper = new ObjectMapper();
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  SlackUserRepository slackUserRepository;
  @MockBean
  AuthControllerAdvice authControllerAdvice;
  @MockBean
  AppUserRepository appUserRepository;
  @MockBean
  GoogleSearchService googleSearchService;

  @Test
  public void test_searchbasic_unauthorizedIfNotAdmin() throws Exception {
    mockMvc
        .perform(
            get("/api/member/search/basic?searchQuery=springboot").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().is(401));
  }

  @Test
  public void test_getAppUsers() throws Exception {
    List<AppUser> expectedAppUser = new ArrayList<AppUser>();
    SlackUser appUser = new AppUser();
    appUser.setEmail("email");
    appUser.setFirstName("First");
    appUser.setLastNameName("Last");
    expectedAppUsers.save(appUser);

    when(slackUserRepository.findAll()).thenReturn(expectedSlackUsers);
    when(authControllerAdvice.getIsAdmin(exampleAuthToken)).thenReturn(true);

    MvcResult response = mockMvc
        .perform(
            get("/api/slackUsers").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().isOk()).andReturn();

    String responseString = response.getResponse().getContentAsString();
    List<SlackUser> slackUsers = mapper.readValue(responseString, new TypeReference<List<SlackUser>>() {
    });

    assertEquals(expectedSlackUsers, slackUsers);

  }
}

