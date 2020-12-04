package edu.ucsb.mapache.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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

  @DeleteMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<String> deleteStudents(@RequestHeader("Authorization") String authorization,
      @PathVariable("id") Long id) {
    AppUser user = authControllerAdvice.getUser(authorization);
    Optional<Student> students = studentRepository.findById(id);
    if (!students.isPresent() || !students.get().getUserId().equals(user.getEmail())) {
      return ResponseEntity.notFound().build();
    }
    studentRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "", produces = "application/json")
  public ResponseEntity<String> getStudents(@RequestHeader("Authorization") String authorization)
      throws JsonProcessingException {
    AppUser user = authControllerAdvice.getUser(authorization);
    Iterable<Student> studentList = studentRepository.findAll();
    ObjectMapper mapper = new ObjectMapper();
    String body = mapper.writeValueAsString(studentList);
    return ResponseEntity.ok().body(body);
  }

  @PostMapping(value = "/upload", produces = "application/json")
  public ResponseEntity<String> uploadCSV(@RequestParam("csv") MultipartFile csv, @RequestHeader("Authorization") String authorization) {
    logger.info("Starting upload CSV");
    String error = "";
    AppUser user = authControllerAdvice.getUser(authorization);
    try(Reader reader = new InputStreamReader(csv.getInputStream())){
      logger.info(new String(csv.getInputStream().readAllBytes()));
      // convert to list of students
      List<Student> uploadedStudents = csvToObjectService.parse(reader, Student.class);
      // loop

      // save list of students into repository
      List<Student> savedStudents = (List<Student>) studentRepository.saveAll(uploadedStudents);
      // convert to json
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