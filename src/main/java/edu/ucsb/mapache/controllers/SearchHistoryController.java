package edu.ucsb.mapache.controllers;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.entities.UserSearch;
import edu.ucsb.mapache.repositories.ChannelRepository;
import edu.ucsb.mapache.repositories.MessageRepository;
import edu.ucsb.mapache.repositories.UserSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import edu.ucsb.mapache.services.GoogleSearchService;

@RestController
@RequestMapping("/api/members/searchhistory")
public class SearchHistoryController {
    private final Logger logger = LoggerFactory.getLogger(SearchHistoryController.class);

    @Autowired
    UserSearchRepository usersearchRepository;
    @Autowired
    private AuthControllerAdvice authControllerAdvice;
    
    public DecodedJWT getJWT(String authorization) {
    return JWT.decode(authorization.substring(7));
    }
   
    private ObjectMapper mapper = new ObjectMapper();

    private ResponseEntity<String> getUnauthorizedResponse(String roleRequired) throws JsonProcessingException {
        Map<String, String> response = new HashMap<String, String>();
        response.put("error", String.format("Unauthorized; only %s may access this resource.", roleRequired));
        String body = mapper.writeValueAsString(response);
        return new ResponseEntity<String>(body, HttpStatus.UNAUTHORIZED);
    }

 
    @GetMapping("/allusersearches")
    public ResponseEntity<String> getSearches(@RequestHeader("Authorization") String authorization)
            throws JsonProcessingException {
        if (!authControllerAdvice.getIsMember(authorization))
            return getUnauthorizedResponse("member");
       
        if(!authControllerAdvice.getIsAdmin(authorization)){
            DecodedJWT jwt = getJWT(authorization);
        Map<String, Object> customClaims = jwt.getClaim(namespace).asMap();
        if (customClaims == null)
            return;
	    String firstName = (String) customClaims.get("given_name");
        String lastName = (String) customClaims.get("family_name");
	    String userid=firstName.concat(lastName);
        
        Iterable<UserSearch> usersearch = usersearchRepository.findByUserID(userid);
        String body = mapper.writeValueAsString(usersearch);
        return ResponseEntity.ok().body(body);
        }
   
        Iterable<UserSearch> usersearch = usersearchRepository.findAll();
        String body = mapper.writeValueAsString(usersearch);
        return ResponseEntity.ok().body(body);
    }
    
    /* @GetMapping("/insert")
    public ResponseEntity<String> basicSearch(@RequestHeader("Authorization") String authorization,
            @RequestParam String searchQuery) throws JsonProcessingException {
        if (!authControllerAdvice.getIsMemberOrAdmin(authorization))
            return getUnauthorizedResponse("member or admin");

        String result = null;
       
        result = googleSearchService.performUserSearch( searchQuery,  authorization);
        
    
        
        return ResponseEntity.ok().body(result);
    }*/
}
