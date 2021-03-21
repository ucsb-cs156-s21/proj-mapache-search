package edu.ucsb.mapache.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.auth0.jwt.interfaces.DecodedJWT;

import edu.ucsb.mapache.services.CSVToObjectService;
import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.entities.AppUser;
import edu.ucsb.mapache.entities.Counter;
import edu.ucsb.mapache.entities.Student;
import edu.ucsb.mapache.entities.Team;
import edu.ucsb.mapache.repositories.CounterRepository;
import edu.ucsb.mapache.repositories.TeamRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/member/counters")
public class CounterController {
    private final Logger logger = LoggerFactory.getLogger(CounterController.class);

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private AuthControllerAdvice authControllerAdvice;
    private ObjectMapper mapper = new ObjectMapper();

    private ResponseEntity<String> getUnauthorizedResponse(String roleRequired) throws JsonProcessingException {
        Map<String, String> response = new HashMap<String, String>();
        response.put("error", String.format("Unauthorized; only %s may access this resource.", roleRequired));
        String body = mapper.writeValueAsString(response);
        return new ResponseEntity<String>(body, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/{key}", produces = "application/json")
    public ResponseEntity<String> getCounter(@RequestHeader("Authorization") String authorization,
            @PathVariable("key") String key) throws JsonProcessingException {
        if (!authControllerAdvice.getIsMemberOrAdmin(authorization))
            return getUnauthorizedResponse("admin or member");
        Optional<Counter> opt = counterRepository.findById(key);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(opt.get());
        return ResponseEntity.ok().body(body);
    }
}
