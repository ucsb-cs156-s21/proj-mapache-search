package edu.ucsb.mapache.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date; 
import java.util.Calendar; 
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
import edu.ucsb.mapache.models.SearchParameters;
import org.springframework.beans.factory.annotation.Value;
import edu.ucsb.mapache.services.GoogleSearchService;
import org.springframework.stereotype.Service;

@Service
public class SearchSupportService {

    private final Logger logger = LoggerFactory.getLogger(SearchSupportService.class);

    @Autowired
    private AppUserRepository appUserRepository;

    @Value("${app.namespace}")
    private String namespace;

    public DecodedJWT getJWT(String authorization) {
        return JWT.decode(authorization.substring(7));
    }

    public AppUser getCurrentUser(String authorization){
        DecodedJWT jwt = getJWT(authorization);
        Map<String, Object> customClaims = jwt.getClaim(namespace).asMap();
        String email = (String) customClaims.get("email");
        logger.info("email={}", email);
        List<AppUser> users = appUserRepository.findByEmail(email);// obtain email of current user
        logger.info("user={}", users);
        AppUser you =null;
        if(users.isEmpty()){
            you = new AppUser();
            you.setEmail(email);
            String firstName = (String) customClaims.get("given_name");
            String lastName = (String) customClaims.get("family_name");
            you.setFirstName(firstName);
            you.setLastName(lastName);
                
            appUserRepository.save(you);
        }else{
            you=users.get(0);
        }
        return you;
    }
    
    public boolean shouldReset(long lastUpdate, long currentTime){
        long lastUpdateDate= (lastUpdate-8*60*60*1000)/(24*60*60*1000);
        long currentDate= (currentTime-8*60*60*1000)/(24*60*60*1000);
        return currentDate>lastUpdateDate;
    }


}