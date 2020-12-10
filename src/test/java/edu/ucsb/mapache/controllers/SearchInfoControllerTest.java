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
import edu.ucsb.mapache.entities.AppUser;
import edu.ucsb.mapache.repositories.AppUserRepository;

@WebMvcTest(value = SearchInfoController.class)
@WithMockUser
public class SearchInfoControllerTest {
  private String exampleAuthToken = "Bearer blah";
  private ObjectMapper mapper = new ObjectMapper();
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  AppUserRepository appUserRepository;
  @MockBean
  AuthControllerAdvice authControllerAdvice;

  @Test
  public void test_getAppUsers_unauthorizedIfNotAdmin() throws Exception {
    mockMvc
        .perform(
            get("/api/searchInfo").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().is(401));
  }

  @Test
  public void test_getAppUsers_getsListOfAppUsers() throws Exception {
    List<AppUser> expectedAppUsers = new ArrayList<AppUser>();
    AppUser appUser = new AppUser();
    appUser.setId(1);
    appUser.setEmail("haixinlin@umail.ucsb.edu");
    appUser.setFirstName("Hunter");
    appUser.setLastName("Lin");
    appUser.setSearchRemain(100);
    appUser.setTime(0);
    expectedAppUsers.add(appUser);

    when(appUserRepository.findAll()).thenReturn(expectedAppUsers);
    when(authControllerAdvice.getIsAdmin(exampleAuthToken)).thenReturn(true);

    MvcResult response = mockMvc
        .perform(
            get("/api/searchInfo").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().isOk()).andReturn();

    String responseString = response.getResponse().getContentAsString();
    List<AppUser> appUsers = mapper.readValue(responseString, new TypeReference<List<AppUser>>() {
    });

    assertEquals(expectedAppUsers, appUsers);

  }
}
