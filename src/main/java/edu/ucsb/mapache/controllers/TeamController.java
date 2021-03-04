package edu.ucsb.mapache.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import edu.ucsb.mapache.repositories.StudentRepository;
import edu.ucsb.mapache.entities.Student;

@RestController
@RequestMapping("/api/member/teams")
public class TeamController {
    private final Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private StudentRepository studentRepository;

    // @Autowired
    // private AuthControllerAdvice authControllerAdvice;
    // private ObjectMapper mapper = new ObjectMapper();
    
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<String> getTeams(@RequestHeader("Authorization") String authorization)
        throws JsonProcessingException {
    Iterable<Student> studentList = studentRepository.findAll();
    Set<String> emailSet = new HashSet<String>();
    for(Student s: studentList){
        emailSet.add(s.getEmail());
    }
    Iterable<String> emailList = new ArrayList<String>(emailSet);

    ObjectMapper mapper = new ObjectMapper();
    String body = mapper.writeValueAsString(emailList);
    return ResponseEntity.ok().body(body);
    }
}
