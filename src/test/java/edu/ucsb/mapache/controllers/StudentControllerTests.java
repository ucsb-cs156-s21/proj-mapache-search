package edu.ucsb.mapache.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.repositories.StudentRepository;

import edu.ucsb.mapache.entities.AppUser;

@WebMvcTest(value = StudentController.class)
@WithMockUser
public class StudentControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  StudentRepository mockStudentRepository;
  @MockBean
  AuthControllerAdvice mockAuthControllerAdvice;

  private String userToken() {
    return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTYiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.MkiS50WhvOFwrwxQzd5Kp3VzkQUZhvex3kQv-CLeS3M";
  }

  @Test
   public void createStudent(String s, Student stud) throws Exception {
     Student expectedStudent = new Student(1L, "email", "team");
     ObjectMapper mapper = new ObjectMapper();
     String requestBody = mapper.writeValueAsString(expectedStudent);
     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
     when(mockStudentRepository.save(any())).thenReturn(expectedStudent);
     MvcResult response = mockMvc
         .perform(post("/api/admin/student").with(csrf()).contentType(MediaType.APPLICATION_JSON)
             .characterEncoding("utf-8").content(requestBody).header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken()))
         .andExpect(status().isOk()).andReturn();

     verify(mockStudentRepository, times(1)).save(expectedStudent);

     String responseString = response.getResponse().getContentAsString();
     Student actualStudent = objectMapper.readValue(responseString, Student.class);
     assertEquals(actualStudent, expectedStudent);
   }


   @Test
   public void deleteStudent(String s) throws Exception {
     Student expectedStudent = new Student(1L, "email", "team");
     when(mockStudentRepository.findById(1L)).thenReturn(Optional.of(expectedStudent));
     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
     MvcResult response = mockMvc         .perform(delete("/api/admin/student/team").with(csrf()).contentType(MediaType.APPLICATION_JSON)
             .characterEncoding("utf-8").header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken()))
         .andExpect(status().isNoContent()).andReturn();
     verify(mockStudentRepository, times(1)).findById(expectedStudent.getId());
     verify(mockStudentRepository, times(1)).deleteById(expectedStudent.getId());

     String responseString = response.getResponse().getContentAsString();

     assertEquals(responseString.length(), 0);
   }


   @Test
   public void getStudent(long Id) throws Exception {
      Student expectedStudent = new Student(1L, "email", "team");
      when(mockStudentRepository.findById(1L)).thenReturn(Optional.of(expectedStudent));
      MvcResult response = mockMvc.perform(get("/api/public/team").contentType("application/json")
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isOk()).andReturn();

      verify(mockStudentRepository, times(1)).findById(1L);

      String responseString = response.getResponse().getContentAsString();
      Student actualStudent = objectMapper.readValue(responseString, Student.class);
      assertEquals(actualStudent, expectedStudent);
   }


   @Test
   public void getStudents(String s) throws Exception {
      List<Student> expectedStudents = new ArrayList<Student>();
      expectedStudents.add(new Student(1L, "email", "team"));
      when(mockStudentRepository.findAll()).thenReturn(expectedStudents);
      MvcResult response = mockMvc.perform(get("/api/public/student").contentType("application/json")
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isOk()).andReturn();

      verify(mockStudentRepository, times(1)).findAll();

      String responseString = response.getResponse().getContentAsString();
      List<Student> actualStudents = objectMapper.readValue(responseString, new TypeReference<List<Student>>() {
      });
      assertEquals(actualStudents, expectedStudents);
   }

   @Test
   public void deleteStudents(String s) throws Exception {
     Student expectedStudent = new Student(1L, "email", "team");
     when(mockStudentRepository.findById(1L)).thenReturn(Optional.of(expectedStudent));
     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
     MvcResult response = mockMvc         .perform(delete("/api/admin/student/team").with(csrf()).contentType(MediaType.APPLICATION_JSON)
             .characterEncoding("utf-8").header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken()))
         .andExpect(status().isNoContent()).andReturn();
     verify(mockStudentRepository, times(1)).findById(expectedStudent.getId());
     verify(mockStudentRepository, times(1)).deleteById(expectedStudent.getId());

     String responseString = response.getResponse().getContentAsString();

     assertEquals(responseString.length(), 0);
   }

   @Test
   public void updateStudent(String email, long id, Student stud) throws Exception {
     Student inputStudent = new Student(1L, "email", "team");
     Student savedStudent = new Student(2L, "email2", "team");
     String body = objectMapper.writeValueAsString(inputStudent);

     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
     when(mockStudentRepository.findById(any(Long.class))).thenReturn(Optional.of(savedStudent));
     when(mockStudentRepository.save(inputStudent)).thenReturn(inputStudent);
     MvcResult response = mockMvc
         .perform(put("/api/admin/student").with(csrf()).contentType(MediaType.APPLICATION_JSON)
             .characterEncoding("utf-8").header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken()).content(body))
         .andExpect(status().isOk()).andReturn();

     verify(mockStudentRepository, times(1)).findById(inputStudent.getId());
     verify(mockStudentRepository, times(1)).save(inputStudent);

     String responseString = response.getResponse().getContentAsString();

     assertEquals(body, responseString);
   }

 }