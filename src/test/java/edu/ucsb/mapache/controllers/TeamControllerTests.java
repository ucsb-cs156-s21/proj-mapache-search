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
import edu.ucsb.mapache.repositories.StudentRepository;
import edu.ucsb.mapache.services.CSVToObjectService;
import edu.ucsb.mapache.entities.AppUser;

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

@WebMvcTest(value = TeamController.class)
@WithMockUser

public class TeamControllerTests {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  StudentRepository mockStudentRepository;
  @MockBean
  AuthControllerAdvice mockAuthControllerAdvice;
  @MockBean
  CSVToObjectService mockCSVToObjectService;

  @MockBean
  Reader mockReader;

  private String userToken() {
    return "blah";
  }

  @Test
  public void testGetAllTeams() throws Exception {
    List<String> expectedTeams = new ArrayList<String>();
    expectedTeams.add("team1");
    ObjectMapper mapper = new ObjectMapper();
    when(mockStudentRepository.selectDistinctTeamname()).thenReturn(expectedTeams);
    when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
    MvcResult response = mockMvc.perform(get("/api/member/teams").contentType("application/json")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isOk()).andReturn();
    verify(mockStudentRepository, times(1)).selectDistinctTeamname();
    String responseString = response.getResponse().getContentAsString();
    List<String> actualTeams = mapper.readValue(responseString, new TypeReference<List<String>>() {
    });
    assertEquals(actualTeams, expectedTeams);
  }

  @Test
  public void testGetStudents_notAdmin() throws Exception {
    List<String> expectedTeams = new ArrayList<String>();
    when(mockStudentRepository.selectDistinctTeamname()).thenReturn(expectedTeams);
    when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(false);
    MvcResult response = mockMvc.perform(get("/api/member/teams").contentType("application/json")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isUnauthorized()).andReturn();
  }
}
