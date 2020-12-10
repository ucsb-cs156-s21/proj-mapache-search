package edu.ucsb.mapache.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.documents.Message;
import edu.ucsb.mapache.repositories.ChannelRepository;
import edu.ucsb.mapache.repositories.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;

@WebMvcTest(value = MessagesController.class)
@WithMockUser
public class MessagesControllerTests {
        private String exampleAuthToken = "token";

        private ObjectMapper mapper = new ObjectMapper();
        @Autowired
        private MockMvc mockMvc;
        @MockBean
        ChannelRepository mockChannelRepository;
        @MockBean
        MessageRepository mockMessageRepository;
        @MockBean
        AuthControllerAdvice mockAuthControllerAdvice;

        @Test
        public void test_usersearch_messages_unauthorizedIfNotMember() throws Exception {
                mockMvc.perform(get("/api/members/messages/usersearch?searchUser=springboot")
                                .contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
                                .andExpect(status().is(401));
        }

        @Test
        public void test_usersearch_messages() throws Exception {
                List<Message> expectedMessages = new ArrayList<Message>();
                when(mockMessageRepository.findByUser("springboot")).thenReturn(expectedMessages);
                when(mockAuthControllerAdvice.getIsMember(anyString())).thenReturn(true);
                MvcResult response = mockMvc.perform(get("/api/members/messages/usersearch?searchUser=springboot")
                                .contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
                                .andExpect(status().isOk()).andReturn();
                String responseString = response.getResponse().getContentAsString();
                List<Message> messages = mapper.readValue(responseString, new TypeReference<List<Message>>() {
                });
                assertEquals(expectedMessages, messages);
        }

        @Test
        public void test_usersearch_messages_with_empty_string() throws Exception {
                List<Message> expectedMessages = new ArrayList<Message>();
                when(mockMessageRepository.findByUser("")).thenReturn(expectedMessages);
                when(mockAuthControllerAdvice.getIsMember(anyString())).thenReturn(true);
                MvcResult response = mockMvc.perform(get("/api/members/messages/usersearch?searchUser=")
                                .contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
                                .andExpect(status().isOk()).andReturn();
                String responseString = response.getResponse().getContentAsString();
                List<Message> messages = mapper.readValue(responseString, new TypeReference<List<Message>>() {
                });
                assertEquals(expectedMessages, messages);
        }

        @Test
        public void test_contentsearch_messages_unauthorizedIfNotMember() throws Exception {
                mockMvc.perform(get("/api/members/messages/contentsearch?searchString=springboot")
                                .contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
                                .andExpect(status().is(401));
        }

        @Test
        public void test_contentsearch_messages() throws Exception {
                List<Message> expectedMessages = new ArrayList<Message>();
                when(mockMessageRepository.findByText("springboot")).thenReturn(expectedMessages);
                when(mockAuthControllerAdvice.getIsMember(anyString())).thenReturn(true);
                MvcResult response = mockMvc.perform(get("/api/members/messages/contentsearch?searchString=springboot")
                                .contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
                                .andExpect(status().isOk()).andReturn();
                String responseString = response.getResponse().getContentAsString();
                List<Message> messages = mapper.readValue(responseString, new TypeReference<List<Message>>() {
                });
                assertEquals(expectedMessages, messages);
        }

        @Test
        public void test_contentsearch_messages_with_empty_string() throws Exception {
                List<Message> expectedMessages = new ArrayList<Message>();
                when(mockMessageRepository.findByText("")).thenReturn(expectedMessages);
                when(mockAuthControllerAdvice.getIsMember(anyString())).thenReturn(true);
                MvcResult response = mockMvc.perform(get("/api/members/messages/contentsearch?searchString=")
                                .contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
                                .andExpect(status().isOk()).andReturn();
                String responseString = response.getResponse().getContentAsString();
                List<Message> messages = mapper.readValue(responseString, new TypeReference<List<Message>>() {
                });
                assertEquals(expectedMessages, messages);
        }

}
