package edu.ucsb.mapache.controllers;

import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.documents.Channel;
import edu.ucsb.mapache.documents.Message;
import edu.ucsb.mapache.documents.SlackUser;
import edu.ucsb.mapache.entities.Admin;
import edu.ucsb.mapache.entities.AppUser;
import edu.ucsb.mapache.repositories.AppUserRepository;
import edu.ucsb.mapache.repositories.ChannelRepository;
import edu.ucsb.mapache.repositories.MessageRepository;
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

@RestController
@RequestMapping("/api/members")
public class ChannelsController {
    private final Logger logger = LoggerFactory.getLogger(ChannelsController.class);

    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private AuthControllerAdvice authControllerAdvice;

    private ObjectMapper mapper = new ObjectMapper();

    private ResponseEntity<String> getUnauthorizedResponse(String roleRequired) throws JsonProcessingException {
        Map<String, String> response = new HashMap<String, String>();
        response.put("error", String.format("Unauthorized; only %s may access this resource.", roleRequired));
        String body = mapper.writeValueAsString(response);
        return new ResponseEntity<String>(body, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/channels")
    public ResponseEntity<String> getChannels(@RequestHeader("Authorization") String authorization)
            throws JsonProcessingException {
        if (!authControllerAdvice.getIsMember(authorization))
            return getUnauthorizedResponse("member");

        Set<Channel> channels = new HashSet<>(channelRepository.findAll());
        String body = mapper.writeValueAsString(channels);

        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/channel/{channel}/messages")
    public ResponseEntity<String> getMessageOfChannel(@RequestHeader("Authorization") String authorization,
                                         @PathVariable("channel") String channel) throws JsonProcessingException {
        if (!authControllerAdvice.getIsMember(authorization))
            return getUnauthorizedResponse("member");

        Set<Message> channels = new HashSet<>(messageRepository.findByChannel(channel));
        String body = mapper.writeValueAsString(channels);

        return ResponseEntity.ok().body(body);
    }
}
