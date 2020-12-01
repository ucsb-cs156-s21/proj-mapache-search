package edu.ucsb.mapache.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.documents.SlackUser;
import edu.ucsb.mapache.entities.Admin;
import edu.ucsb.mapache.entities.AppUser;
import edu.ucsb.mapache.repositories.AdminRepository;
import edu.ucsb.mapache.repositories.AppUserRepository;
import edu.ucsb.mapache.repositories.SlackUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/member/search")
public class SearchController {
    private final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private AuthControllerAdvice authControllerAdvice;

    private ObjectMapper mapper = new ObjectMapper();

    private ResponseEntity<String> getUnauthorizedResponse(String roleRequired) throws JsonProcessingException {
        Map<String, String> response = new HashMap<String, String>();
        response.put("error", String.format("Unauthorized; only %s may access this resource.", roleRequired));
        String body = mapper.writeValueAsString(response);
        return new ResponseEntity<String>(body, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/basic")
    public ResponseEntity<String> basicSearch(@RequestHeader("Authorization") String authorization, 
        @RequestParam String searchQuery)
        throws JsonProcessingException {
        if (!authControllerAdvice.getIsMember(authorization))
            return getUnauthorizedResponse("member");
        //replace the next two lines with code that actually gets the search results from google
        Map<String, String> fakeMap = new HashMap<String, String>();
        fakeMap.put("key","value");
        String body = mapper.writeValueAsString(fakeMap);
        //the body that's passed into line 58 should be the JSON for the search results
        return ResponseEntity.ok().body(body);
    }
}