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
// import edu.ucsb.mapache.entities.Student2;
import edu.ucsb.mapache.repositories.StudentRepository;
// import edu.ucsb.mapache.repositories.TeamRepository;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/students")
public class StudentController {
  private final Logger logger = LoggerFactory.getLogger(StudentController.class);

  @Autowired
  private StudentRepository studentRepository;

  // @Autowired
  // private TeamRepository team1Repository;

  // @Autowired
  // private StudentRepository team1Repository;

  // @Autowired
  // private StudentRepository team2Repository;

  // @Autowired
  // private StudentRepository team3Repository;

  @Autowired
  CSVToObjectService<Student> csvToObjectService;

  // @Autowired
  // CSVToObjectService<Student2> csvToObjectService2;

  @Autowired
  private AuthControllerAdvice authControllerAdvice;

  private ObjectMapper mapper = new ObjectMapper();


  // @GetMapping(value = "/team1", produces = "application/json")
  // public ResponseEntity<String> getTeam1Students(@RequestHeader("Authorization") String authorization)
  //     throws JsonProcessingException {
  //   AppUser user = authControllerAdvice.getUser(authorization);
  //   Iterable<Student2> studentList = team1Repository.findAll();
  //   ObjectMapper mapper = new ObjectMapper();
  //   String body = mapper.writeValueAsString(studentList);
  //   return ResponseEntity.ok().body(body);
  // }

  // finished

  @PutMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<String> updateStudent(@RequestHeader("Authorization") String authorization,
      @PathVariable("id") Long id, @RequestBody @Valid Student incomingStudent)
      throws JsonProcessingException {
    AppUser user = authControllerAdvice.getUser(authorization);
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
    AppUser user = authControllerAdvice.getUser(authorization);
    Optional<Student> students = studentRepository.findById(id);
    if (!students.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    studentRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(value = "", produces = "application/json")
  public ResponseEntity<String> deleteStudents(@RequestHeader("Authorization") String authorization) {
    AppUser user = authControllerAdvice.getUser(authorization);
    Iterable<Student> studentList = studentRepository.findAll();
    for(Student student : studentList){
      Long id = student.getId();
      studentRepository.deleteById(id);
    }
    //team1Repository.deleteAll();
    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "", produces = "application/json")
  public ResponseEntity<String> getStudents(@RequestHeader("Authorization") String authorization)
      throws JsonProcessingException {
    AppUser user = authControllerAdvice.getUser(authorization);
    Iterable<Student> studentList = studentRepository.findAll();
    // Iterable<Student> studentList = team1Repository.findAll();
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
    AppUser user = authControllerAdvice.getUser(authorization);
    Student savedStudent = studentRepository.save(student);
    String body = mapper.writeValueAsString(savedStudent);
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

    // try(Reader reader = new InputStreamReader(csv.getInputStream())){
    //   logger.info(new String(csv.getInputStream().readAllBytes()));
      
    //   // list of uploaded students
    //   List<Student2> uploadedTeam1Students = csvToObjectService2.parse(reader, Student2.class);

    //   team1Repository.deleteAll();

    //   logger.info("Entering loop");
    //   for(int i = 0; i < uploadedTeam1Students.size(); i++){
    //     logger.info("Entered loop");
    //     logger.info(uploadedTeam1Students.get(i).getTeamName());
    //     if(uploadedTeam1Students.get(i).getTeamName().equals("team1")){
    //       logger.info("Entering team1");
    //       logger.info(uploadedTeam1Students.get(i).getEmail());
    //       team1Repository.save(uploadedTeam1Students.get(i));
    //       //team1Students.add(uploadedStudents.get(i));
    //     }
    //     // else{
    //     //   team1Students.remove(uploadedStudents.get(i));
    //     // }
    //   }

    //   List<Student2> team1Students = team1Repository.findAll();

    //   logger.info("Exited loop");
    //   logger.info("Entered loop2");
    //   for(int i = 0; i < team1Students.size(); i++){
    //     logger.info("iteration");
    //     System.out.println(i);
    //     logger.info(team1Students.get(i).toString());
    //   }
    //   String body = mapper.writeValueAsString(team1Students);
    //   return ResponseEntity.ok().body(body);
    // }catch(IOException e){
    //   logger.error(e.toString());
    //   throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing CSV", e);
    // } catch(RuntimeException e){
    //   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed CSV", e);
    // }
  }
}