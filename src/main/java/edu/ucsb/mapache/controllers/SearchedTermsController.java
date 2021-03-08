package edu.ucsb.mapache.controllers;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.entities.Search;
import edu.ucsb.mapache.repositories.SearchRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/searchedTerms")
public class SearchedTermsController {
  private final Logger logger = LoggerFactory.getLogger(SearchedTermsController.class);
  @Autowired
  private SearchRepository searchRepository;
  
  @Autowired
  private AuthControllerAdvice authControllerAdvice;
  private ObjectMapper mapper = new ObjectMapper();
  
  private ResponseEntity<String> getUnauthorizedResponse(String roleRequired) throws JsonProcessingException {
      Map<String, String> response = new HashMap<String, String>();
      response.put("error", String.format("Unauthorized; only %s may access this resource.", roleRequired));
      String body = mapper.writeValueAsString(response);
      return new ResponseEntity<String>(body, HttpStatus.UNAUTHORIZED);
  }
  
  @GetMapping(value = "", produces = "application/json")
  public ResponseEntity<String> getSearchedTerms(@RequestHeader("Authorization") String authorization)
      throws JsonProcessingException {
    if (!authControllerAdvice.getIsAdmin(authorization))
            return getUnauthorizedResponse("admin");
    Iterable<Search> searchList = searchRepository.findAll();
    ObjectMapper mapper = new ObjectMapper();
    String body = mapper.writeValueAsString(searchList);
    return ResponseEntity.ok().body(body);
  }
}