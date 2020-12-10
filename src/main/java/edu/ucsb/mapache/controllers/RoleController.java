package edu.ucsb.mapache.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.entities.Admin;
import edu.ucsb.mapache.entities.AppUser;
import edu.ucsb.mapache.repositories.AdminRepository;
import edu.ucsb.mapache.repositories.AppUserRepository;
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
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api")
public class RoleController {
  private final Logger logger = LoggerFactory.getLogger(RoleController.class);

  @Autowired
  AppUserRepository appUserRepository;

  @Autowired
  AdminRepository adminRepository;

  @Autowired
  private AuthControllerAdvice authControllerAdvice;

  private ObjectMapper mapper = new ObjectMapper();

  private ResponseEntity<String> getUnauthorizedResponse(String roleRequired) throws JsonProcessingException {
    Map<String, String> response = new HashMap<String, String>();
    response.put("error", String.format("Unauthorized; only %s may access this resource.", roleRequired));
    String body = mapper.writeValueAsString(response);
    return new ResponseEntity<String>(body, HttpStatus.UNAUTHORIZED);
  }

  @GetMapping("/users")
  public ResponseEntity<String> users(@RequestHeader("Authorization") String authorization)
      throws JsonProcessingException {
    if (!authControllerAdvice.getIsAdmin(authorization))
      return getUnauthorizedResponse("admin");
    Iterable<AppUser> users = appUserRepository.findAll();
    String body = mapper.writeValueAsString(users);
    return ResponseEntity.ok().body(body);
  }

  @GetMapping("/admins")
  public ResponseEntity<String> admins(@RequestHeader("Authorization") String authorization)
      throws JsonProcessingException {
    if (!authControllerAdvice.getIsAdmin(authorization))
      return getUnauthorizedResponse("admin");
    Iterable<Admin> admins = adminRepository.findAll();
    String body = mapper.writeValueAsString(admins);
    return ResponseEntity.ok().body(body);
  }

  @PutMapping("/admins")
  public ResponseEntity<String> admins(@RequestHeader("Authorization") String authorization,
      @RequestBody @Valid Admin admin) throws JsonProcessingException {
    if (!authControllerAdvice.getIsAdmin(authorization))
      return getUnauthorizedResponse("admin");
    Admin savedAdmin = adminRepository.save(admin);
    String body = mapper.writeValueAsString(savedAdmin);
    return ResponseEntity.ok().body(body);
  }

  @DeleteMapping("/admins/{id}")
  public ResponseEntity<String> admins(@RequestHeader("Authorization") String authorization,
      @PathVariable("id") Long id) throws JsonProcessingException {
    if (!authControllerAdvice.getIsAdmin(authorization))
      return getUnauthorizedResponse("admin");
    Optional<Admin> admin = adminRepository.findById(id);
    if (!admin.isPresent())
      return ResponseEntity.notFound().build();
    adminRepository.delete(admin.get());
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/myRole")
  public ResponseEntity<String> myRole(@RequestHeader("Authorization") String authorization)
      throws JsonProcessingException {
    String role = authControllerAdvice.getRole(authorization);
    Map<String, String> response = new HashMap<>();
    response.put("role", role);
    String body = mapper.writeValueAsString(response);
    return ResponseEntity.ok().body(body);
  }

  // Added two functions below to modify DB to add API Token field
  
  @GetMapping("/apiKey")
  public ResponseEntity<String> getApiToken(@RequestHeader("Authorization") String authorization)
      throws JsonProcessingException {
        AppUser user = authControllerAdvice.getUser(authorization);
        String userToken = user.getApiToken();
        return ResponseEntity.ok().body(userToken);
  }

  //Sets API Token

<<<<<<< HEAD
  @PutMapping(value = "/api/addApiKey", produces = "application/json")
  public ResponseEntity<String> setApiToken(@RequestHeader("Authorization") String authorization, 
  @RequestBody @Valid String token) 
=======
  @PutMapping("/apiKey/{token}")
  public ResponseEntity<String> setApiToken(@RequestHeader("Authorization") String authorization, 
  @PathVariable("token") String token) 
>>>>>>> 6dca1329b74d108ad745acb32b232132ce3bb751
      throws JsonProcessingException {
        AppUser user = authControllerAdvice.getUser(authorization);
        user.setApiToken(token);
        appUserRepository.save(user);
        return new ResponseEntity<> (HttpStatus.NO_CONTENT);
  }
}
