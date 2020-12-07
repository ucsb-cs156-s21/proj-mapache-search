package edu.ucsb.mapache.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.documents.Message;
import edu.ucsb.mapache.documents.SlackUser;
import edu.ucsb.mapache.repositories.MessageRepository;
import edu.ucsb.mapache.repositories.SlackUserRepository;

@WebMvcTest(value = MessagesController.class)
@WithMockUser
public class MessagesControllerTests {

    private String exampleAuthToken = "Bearer blah";
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuthControllerAdvice authControllerAdvice;

    @MockBean
    MessageRepository messageRepository;

    @Test
    public void test_getUnauthorizedResponse() throws Exception {
        mockMvc.perform(get("/api/messages").contentType("application/json").header(HttpHeaders.AUTHORIZATION,
                exampleAuthToken)).andExpect(status().is(401));
    }

    @Test
    public void test_getMembers() throws Exception {
        List<Message> expectedMessages = new ArrayList<Message>();

        Message m1 = new Message("messageId", "messageType", "messageSubtype", "messageTs", "messageUser",
                "messageText", "messageChannel");
        Message m2 = new Message("messageId", "messageType", "messageSubtype", "messageTs", "messageUser",
                "messageText", "messageChannel");

        expectedMessages.add(m1);
        expectedMessages.add(m2);

        when(messageRepository.findAll()).thenReturn(expectedMessages);
        when(authControllerAdvice.getIsMember(exampleAuthToken)).thenReturn(true);

        MvcResult response = mockMvc.perform(get("/api/messages").contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION, exampleAuthToken)).andExpect(status().isOk()).andReturn();

        String responseString = response.getResponse().getContentAsString();
        List<Message> messages = mapper.readValue(responseString, new TypeReference<List<Message>>() {
        });

        assertEquals(expectedMessages, messages);

    }
}
