package edu.ucsb.mapache.controllers;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.*;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.ucsb.mapache.services.CSVToObjectService;
import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.entities.AppUser;
import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.repositories.StudentRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/students")
public class StudentController {
  private final Logger logger = LoggerFactory.getLogger(StudentController.class);
  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  CSVToObjectService<Student> csvToObjectService;
  
  @Autowired
  private AuthControllerAdvice authControllerAdvice;
  private ObjectMapper mapper = new ObjectMapper();
  
  private ResponseEntity<String> getUnauthorizedResponse(String roleRequired) throws JsonProcessingException {
      Map<String, String> response = new HashMap<String, String>();
      response.put("error", String.format("Unauthorized; only %s may access this resource.", roleRequired));
      String body = mapper.writeValueAsString(response);
      return new ResponseEntity<String>(body, HttpStatus.UNAUTHORIZED);
  }
  @PutMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<String> updateStudent(@RequestHeader("Authorization") String authorization,
      @PathVariable("id") Long id, @RequestBody @Valid Student incomingStudent)
      throws JsonProcessingException {
    Optional<Student> student = studentRepository.findById(id);
    if (!student.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    if (!incomingStudent.getId().equals(id)) {
      return ResponseEntity.badRequest().build();
    }
    studentRepository.save(incomingStudent);
    String body = mapper.writeValueAsString(incomingStudent);
    return ResponseEntity.ok().body(body);
  }
  @DeleteMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<String> deleteStudent(@RequestHeader("Authorization") String authorization,
      @PathVariable("id") Long id) {
    if (!authControllerAdvice.getIsAdmin(authorization))
      return getUnauthorizedResponse("admin");
    Optional<Student> students = studentRepository.findById(id);
    if (!students.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    studentRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
  @DeleteMapping(value = "", produces = "application/json")
  public ResponseEntity<String> deleteStudents(@RequestHeader("Authorization") String authorization) {
    if (!authControllerAdvice.getIsAdmin(authorization))
      return getUnauthorizedResponse("admin");
    studentRepository.deleteAll();
    return ResponseEntity.noContent().build();
  }
  @GetMapping(value = "", produces = "application/json")
  public ResponseEntity<String> getStudents(@RequestHeader("Authorization") String authorization)
      throws JsonProcessingException {
    if (!authControllerAdvice.getIsAdmin(authorization))
            return getUnauthorizedResponse("admin");
    Iterable<Student> studentList = studentRepository.findAll();
    ObjectMapper mapper = new ObjectMapper();
    String body = mapper.writeValueAsString(studentList);
    return ResponseEntity.ok().body(body);
  }
  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<String> getStudent(@PathVariable("id") Long id) throws JsonProcessingException {
    Optional<Student> student = studentRepository.findById(id);
    if (student.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    ObjectMapper mapper = new ObjectMapper();
    String body = mapper.writeValueAsString(student.get());
    return ResponseEntity.ok().body(body);
  }
  @PostMapping(value = "", produces = "application/json")
  public ResponseEntity<String> createStudent(@RequestHeader("Authorization") String authorization,
      @RequestBody @Valid Student student) throws JsonProcessingException {
    if (!authControllerAdvice.getIsAdmin(authorization))
      return getUnauthorizedResponse("admin");
    Student savedStudent = studentRepository.save(student);
    String body = mapper.writeValueAsString(savedStudent);
    return ResponseEntity.ok().body(body);
  }
  @PostMapping(value = "/upload", produces = "application/json")
  public ResponseEntity<String> uploadCSV(@RequestParam("csv") MultipartFile csv, @RequestHeader("Authorization") String authorization) {
    logger.info("Starting upload CSV");
    String error = "";
    if (!authControllerAdvice.getIsAdmin(authorization))
      return getUnauthorizedResponse("admin");
    try(Reader reader = new InputStreamReader(csv.getInputStream())){
      logger.info(new String(csv.getInputStream().readAllBytes()));
      List<Student> uploadedStudents = csvToObjectService.parse(reader, Student.class);
      List<Student> savedStudents = (List<Student>) studentRepository.saveAll(uploadedStudents);
      String body = mapper.writeValueAsString(savedStudents);
      return ResponseEntity.ok().body(body);
    } catch(IOException e){
      logger.error(e.toString());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing CSV", e);
    } catch(RuntimeException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed CSV", e);
    }
  }
}