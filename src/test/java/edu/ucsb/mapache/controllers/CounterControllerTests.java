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
import edu.ucsb.mapache.entities.Team;
import edu.ucsb.mapache.repositories.CounterRepository;
import edu.ucsb.mapache.repositories.TeamRepository;
import edu.ucsb.mapache.services.CSVToObjectService;
import edu.ucsb.mapache.entities.AppUser;
import edu.ucsb.mapache.entities.Counter;

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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

@WebMvcTest(value = CounterController.class)
@WithMockUser

public class CounterControllerTests {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  CounterRepository mockCounterRepository;
  @MockBean
  AuthControllerAdvice mockAuthControllerAdvice;
  
  private String userToken() {
    return "blah";
  }

  @Test
  public void testGetCounter() throws Exception {

    ObjectMapper mapper = new ObjectMapper();

    String key = "foo";
    Counter expectedCounter = new Counter(key,0,"a test counter called foo");
    when(mockCounterRepository.findById("foo")).thenReturn(Optional.of(expectedCounter));

    when(mockAuthControllerAdvice.getIsMember(anyString())).thenReturn(true);
    MvcResult response = mockMvc.perform(get("/api/member/counters/foo").contentType("application/json")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isOk()).andReturn();
    verify(mockCounterRepository, times(1)).findById("foo");

    String responseString = response.getResponse().getContentAsString();
    Counter actualCounter = mapper.readValue(responseString, Counter.class); 
    assertEquals(expectedCounter, actualCounter);
  }

  @Test
  public void testGetUnknownCounter() throws Exception {

    when(mockCounterRepository.findById("bar")).thenReturn(Optional.empty());

    when(mockAuthControllerAdvice.getIsMember(anyString())).thenReturn(true);
    MvcResult response = mockMvc.perform(get("/api/member/counters/bar").contentType("application/json")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isNotFound()).andReturn();
  }

  @Test
  public void testGetCounter_notAdmin() throws Exception {
    when(mockAuthControllerAdvice.getIsMember(anyString())).thenReturn(false);
    MvcResult response = mockMvc.perform(get("/api/member/counters/foo").contentType("application/json")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isUnauthorized()).andReturn();
  }
}
