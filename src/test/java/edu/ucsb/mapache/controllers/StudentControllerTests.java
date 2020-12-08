// package edu.ucsb.mapache.controllers;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

// import com.fasterxml.jackson.core.type.TypeReference;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import edu.ucsb.mapache.advice.AuthControllerAdvice;
// import edu.ucsb.mapache.entities.Student;
// import edu.ucsb.mapache.repositories.StudentRepository;

// import edu.ucsb.mapache.entities.AppUser;

// @WebMvcTest(value = StudentController.class)
// @WithMockUser
// public class StudentControllerTests {

//   @Autowired
//   private MockMvc mockMvc;

//   @Autowired
//   private ObjectMapper objectMapper;

//   @MockBean
//   StudentRepository mockStudentRepository;
//   @MockBean
//   AuthControllerAdvice mockAuthControllerAdvice;

//   private String userToken() {
//     return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTYiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.MkiS50WhvOFwrwxQzd5Kp3VzkQUZhvex3kQv-CLeS3M";
//   }

//   @Test
//   public void testGetStudents() throws Exception {
//     List<Student> expectedStudents = new ArrayList<Student>();
//     expectedStudents.add(new Student(1L, "course 1", "F20", "fname", "lname", "email"));
//     when(mockStudentRepository.findAll()).thenReturn(expectedStudents);
//     MvcResult response = mockMvc.perform(get("/api/public/courses").contentType("application/json")
//         .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isOk()).andReturn();

//     verify(mockStudentRepository, times(1)).findAll();

//     String responseString = response.getResponse().getContentAsString();
//     List<Student> actualStudents = objectMapper.readValue(responseString, new TypeReference<List<Student>>() {
//     });
//     assertEquals(actualStudents, expectedStudents);
//   }

//   @Test
//   public void testGetASingleStudent() throws Exception {
//     Student expectedStudent = new Student(1L, "course 1", "F20", "fname", "lname", "email");
//     // mockito is the library that allows us to do this when stuff
//     when(mockStudentRepository.findById(1L)).thenReturn(Optional.of(expectedStudent));
//     MvcResult response = mockMvc.perform(get("/api/public/courses/1").contentType("application/json")
//         .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isOk()).andReturn();

//     verify(mockStudentRepository, times(1)).findById(1L);

//     String responseString = response.getResponse().getContentAsString();
//     Student actualStudent = objectMapper.readValue(responseString, Student.class);
//     assertEquals(actualStudent, expectedStudent);
//   }

//   @Test
//   public void testGetANonExistingStudent() throws Exception {
//     when(mockStudentRepository.findById(99999L)).thenReturn(Optional.ofNullable(null));
//     mockMvc.perform(get("/api/public/courses/99999").contentType("application/json").header(HttpHeaders.AUTHORIZATION,
//         "Bearer " + userToken())).andExpect(status().isNotFound());
//   }

//   @Test
//   public void testSaveStudent() throws Exception {
//     Student expectedStudent = new Student(1L, "course 1", "F20", "fname", "lname", "email");
//     ObjectMapper mapper = new ObjectMapper();
//     String requestBody = mapper.writeValueAsString(expectedStudent);
//     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
//     when(mockStudentRepository.save(any())).thenReturn(expectedStudent);
//     MvcResult response = mockMvc
//         .perform(post("/api/admin/courses").with(csrf()).contentType(MediaType.APPLICATION_JSON)
//             .characterEncoding("utf-8").content(requestBody).header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken()))
//         .andExpect(status().isOk()).andReturn();

//     verify(mockStudentRepository, times(1)).save(expectedStudent);

//     String responseString = response.getResponse().getContentAsString();
//     Student actualStudent = objectMapper.readValue(responseString, Student.class);
//     assertEquals(actualStudent, expectedStudent);
//   }

//   @Test
//   public void test_saveStudent_unauthorizedIfNotAdmin() throws Exception {
//     Student expectedStudent = new Student(1L, "course 1", "F20", "fname", "lname", "email");
//     ObjectMapper mapper = new ObjectMapper();
//     String requestBody = mapper.writeValueAsString(expectedStudent);
//     mockMvc
//         .perform(post("/api/admin/courses").with(csrf()).contentType(MediaType.APPLICATION_JSON)
//             .characterEncoding("utf-8").content(requestBody).header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken()))
//         .andExpect(status().isUnauthorized());
//   }

//   @Test
//   public void testUpdateStudent_courseExists_updateValues() throws Exception {
//     Student inputStudent = new Student(1L, "new course 1", "F20", "fname", "lname", "email");
//     Student savedStudent = new Student(1L, "old course 1", "W21", "first name", "last name", "myemail");
//     String body = objectMapper.writeValueAsString(inputStudent);

//     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
//     when(mockStudentRepository.findById(any(Long.class))).thenReturn(Optional.of(savedStudent));
//     when(mockStudentRepository.save(inputStudent)).thenReturn(inputStudent);
//     MvcResult response = mockMvc
//         .perform(put("/api/admin/courses/1").with(csrf()).contentType(MediaType.APPLICATION_JSON)
//             .characterEncoding("utf-8").header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken()).content(body))
//         .andExpect(status().isOk()).andReturn();

//     verify(mockStudentRepository, times(1)).findById(inputStudent.getId());
//     verify(mockStudentRepository, times(1)).save(inputStudent);

//     String responseString = response.getResponse().getContentAsString();

//     assertEquals(body, responseString);
//   }

//   @Test
//   public void testUpdateStudent_unauthorizedIfNotAdmin() throws Exception {
//     Student inputStudent = new Student(1L, "new course 1", "F20", "fname", "lname", "email");
//     String body = objectMapper.writeValueAsString(inputStudent);

//     mockMvc
//         .perform(put("/api/admin/courses/1").with(csrf()).contentType(MediaType.APPLICATION_JSON)
//             .characterEncoding("utf-8").header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken()).content(body))
//         .andExpect(status().isUnauthorized());
//   }

//   @Test
//   public void testUpdateStudent_courseNotFound() throws Exception {
//     Student inputStudent = new Student(1L, "new course 1", "F20", "fname", "lname", "email");
//     String body = objectMapper.writeValueAsString(inputStudent);
//     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
//     when(mockStudentRepository.findById(1L)).thenReturn(Optional.empty());
//     mockMvc
//         .perform(put("/api/admin/courses/1").with(csrf()).contentType(MediaType.APPLICATION_JSON)
//             .characterEncoding("utf-8").header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken()).content(body))
//         .andExpect(status().isNotFound()).andReturn();
//     verify(mockStudentRepository, times(1)).findById(1L);
//     verify(mockStudentRepository, times(0)).save(any(Student.class));
//   }

//   @Test
//   public void testUpdateStudent_courseAtPathOwned_butTryingToOverwriteAnotherStudent() throws Exception {
//     Student inputStudent = new Student(1L, "new course 1 trying to overwrite at id 1", "F20", "fname", "lname", "email");
//     Student savedStudent = new Student(2L, "new course 1", "F20", "fname", "lname", "email");
//     String body = objectMapper.writeValueAsString(inputStudent);
//     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
//     when(mockStudentRepository.findById(any(Long.class))).thenReturn(Optional.of(savedStudent));
//     mockMvc
//         .perform(put("/api/admin/courses/2").with(csrf()).contentType(MediaType.APPLICATION_JSON)
//             .characterEncoding("utf-8").header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken()).content(body))
//         .andExpect(status().isBadRequest()).andReturn();
//     verify(mockStudentRepository, times(1)).findById(2L);
//     verify(mockStudentRepository, times(0)).save(any(Student.class));
//   }

//   @Test
//   public void testDeleteStudent_courseExists() throws Exception {
//     Student expectedStudent = new Student(1L, "new course 1", "F20", "fname", "lname", "email");
//     when(mockStudentRepository.findById(1L)).thenReturn(Optional.of(expectedStudent));
//     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
//     MvcResult response = mockMvc
//         .perform(delete("/api/admin/courses/1").with(csrf()).contentType(MediaType.APPLICATION_JSON)
//             .characterEncoding("utf-8").header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken()))
//         .andExpect(status().isNoContent()).andReturn();
//     verify(mockStudentRepository, times(1)).findById(expectedStudent.getId());
//     verify(mockStudentRepository, times(1)).deleteById(expectedStudent.getId());

//     String responseString = response.getResponse().getContentAsString();

//     assertEquals(responseString.length(), 0);
//   }

//   @Test
//   public void testDeleteStudent_unauthorizedIfNotAdmin() throws Exception {
//     mockMvc
//         .perform(delete("/api/admin/courses/1").with(csrf()).contentType(MediaType.APPLICATION_JSON)
//             .characterEncoding("utf-8").header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken()))
//         .andExpect(status().isUnauthorized());
//   }

//   @Test
//   public void testDeleteStudent_courseNotFound() throws Exception {
//     long id = 1L;
//     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
//     when(mockStudentRepository.findById(id)).thenReturn(Optional.empty());
//     mockMvc
//         .perform(delete("/api/admin/courses/1").with(csrf()).contentType(MediaType.APPLICATION_JSON)
//             .characterEncoding("utf-8").header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken()))
//         .andExpect(status().isNotFound()).andReturn();
//     verify(mockStudentRepository, times(1)).findById(id);
//     verify(mockStudentRepository, times(0)).deleteById(id);
//   }

//   @Test
//   public void testGetMyStudents() throws Exception {
//     List<Student> expectedStudents = new ArrayList<Student>();
//     expectedStudents.add(new Student(1L, "course 1", "F20", "fname", "lname", "email"));
//     when(mockStudentRepository.findAll()).thenReturn(expectedStudents);
//     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
//     MvcResult response = mockMvc.perform(get("/api/member/courses").contentType("application/json")
//         .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isOk()).andReturn();

//     verify(mockStudentRepository, times(1)).findAll();

//     String responseString = response.getResponse().getContentAsString();
//     List<Student> actualStudents = objectMapper.readValue(responseString, new TypeReference<List<Student>>() {
//     });
//     assertEquals(actualStudents, expectedStudents);
//   }

//   @Test
//   public void testGetMyStudents_notFound() throws Exception {
//     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(true);
//     mockMvc.perform(get("/api/member/courses").contentType("application/json")
//       .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isNotFound());
//   }

//   @Test
//   public void testGetMyStudents_notAdmin() throws Exception {
//     List<Student> expectedStudents = new ArrayList<Student>();
//     expectedStudents.add(new Student(1L, "course 1", "F20", "fname", "lname", "email"));
//     when(mockStudentRepository.findAllByInstructorEmail("email")).thenReturn(expectedStudents);
//     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(false);
//     AppUser user = new AppUser(1L, "email", "Seth", "VanB");
//     when(mockAuthControllerAdvice.getUser(anyString())).thenReturn(user);
//     MvcResult response = mockMvc.perform(get("/api/member/courses").contentType("application/json")
//         .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isOk()).andReturn();

//     verify(mockStudentRepository, times(1)).findAllByInstructorEmail("email");

//     String responseString = response.getResponse().getContentAsString();
//     List<Student> actualStudents = objectMapper.readValue(responseString, new TypeReference<List<Student>>() {
//     });
//     assertEquals(actualStudents, expectedStudents);
//   }


//   @Test
//   public void testGetMyStudentsUnauthorized() throws Exception {
//     List<Student> expectedStudents = new ArrayList<Student>();
//     when(mockStudentRepository.findAllByInstructorEmail("email")).thenReturn(expectedStudents);
//     when(mockAuthControllerAdvice.getIsAdmin(anyString())).thenReturn(false);
//     AppUser user = new AppUser(1L, "email", "Seth", "VanB");
//     when(mockAuthControllerAdvice.getUser(anyString())).thenReturn(user);
//     mockMvc.perform(get("/api/member/courses").contentType("application/json")
//         .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isUnauthorized());
//   }

//   @Test
//   public void testGetMyStudentsInstructor() throws Exception {
//     List<Student> expectedStudents = new ArrayList<Student>();
//     expectedStudents.add(new Student(1L, "course 1", "F20", "fname", "lname", "email"));
//     when(mockStudentRepository.findAllByInstructorEmail("email")).thenReturn(expectedStudents);
//     when(mockAuthControllerAdvice.getIsMember(anyString())).thenReturn(true);
//     MvcResult response = mockMvc.perform(get("/api/member/courses/forInstructor/email").contentType("application/json")
//         .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isOk()).andReturn();

//     verify(mockStudentRepository, times(1)).findAllByInstructorEmail("email");

//     String responseString = response.getResponse().getContentAsString();
//     List<Student> actualStudents = objectMapper.readValue(responseString, new TypeReference<List<Student>>() {
//     });
//     assertEquals(actualStudents, expectedStudents);
//   }

//   @Test
//   public void testGetMyStudentsInstructorUnauthorized() throws Exception {
//     mockMvc.perform(get("/api/member/courses/forInstructor/email").contentType("application/json")
//     .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken())).andExpect(status().isUnauthorized());
//   }
// }