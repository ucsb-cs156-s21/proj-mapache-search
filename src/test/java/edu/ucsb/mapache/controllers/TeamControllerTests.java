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
import edu.ucsb.mapache.repositories.TeamRepository;
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
  TeamRepository mockTeamRepository;
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
    List<Team> expectedTeams = new ArrayList<Team>();
    expectedTeams.add(new Team(1L, "team1", "team description"));
    ObjectMapper mapper = new ObjectMapper();
    when(mockTeamRepository.findAll()).thenReturn(expectedTeams);
    when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
    MvcResult response = mockMvc.perform(get("/api/member/teams").contentType("application/json")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isOk()).andReturn();
    verify(mockTeamRepository, times(1)).findAll();
    String responseString = response.getResponse().getContentAsString();
    List<Team> actualTeams = mapper.readValue(responseString, new TypeReference<List<Team>>() {
    });
    assertEquals(actualTeams, expectedTeams);
  }

  @Test
  public void testGetStudents_notAdmin() throws Exception {
    List<Team> expectedTeams = new ArrayList<Team>();
    when(mockTeamRepository.findAll()).thenReturn(expectedTeams);
    when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(false);
    MvcResult response = mockMvc.perform(get("/api/member/teams").contentType("application/json")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isUnauthorized()).andReturn();
  }
}
