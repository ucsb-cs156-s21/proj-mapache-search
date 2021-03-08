package edu.ucsb.mapache.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.io.Reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.repositories.SearchRepository;
import edu.ucsb.mapache.repositories.StudentRepository;
import edu.ucsb.mapache.services.CSVToObjectService;
import edu.ucsb.mapache.entities.AppUser;
import edu.ucsb.mapache.entities.Search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(value = SearchedTermsController.class)
@WithMockUser
public class SearchedTermsControllerTests {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  SearchRepository mockSearchRepository;
  @MockBean
  AuthControllerAdvice mockAuthControllerAdvice;

  @MockBean
  Reader mockReader;
  private String userToken() {
    return "blah";
  }

  @Test
  public void getAllSearches() throws Exception {
  List<Search> expectedSearches = new ArrayList<Search>();
  expectedSearches.add(new Search( 34L, "github", 1));
  ObjectMapper mapper = new ObjectMapper();
  String requestBody = mapper.writeValueAsString(expectedSearches);
  when(mockSearchRepository.findAll()).thenReturn(expectedSearches);
  when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
  MvcResult response = mockMvc.perform(get("/api/searchedTerms").contentType("application/json")
      .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isOk()).andReturn();
  verify(mockSearchRepository, times(1)).findAll();
  String responseString = response.getResponse().getContentAsString();
  List<Search> actualStudents = objectMapper.readValue(responseString, new TypeReference<List<Search>>() {
  });
  assertEquals(actualStudents, expectedSearches);
  }


  @Test
  public void testGetSearches_notAdmin() throws Exception {
    when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(false);
    MvcResult response = mockMvc.perform(get("/api/searchedTerms").contentType("application/json")
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isUnauthorized()).andReturn();
  }
}