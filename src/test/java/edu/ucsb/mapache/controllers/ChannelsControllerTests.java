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
import edu.ucsb.mapache.documents.Channel;
import edu.ucsb.mapache.documents.ChannelPurpose;
import edu.ucsb.mapache.documents.ChannelTopic;
import edu.ucsb.mapache.documents.SlackUser;
import edu.ucsb.mapache.repositories.ChannelRepository;
import edu.ucsb.mapache.repositories.SlackUserRepository;
import edu.ucsb.mapache.repositories.MessageRepository;


@WebMvcTest(value = ChannelsController.class)
@WithMockUser
public class ChannelsControllerTests {
  
  private String exampleAuthToken = "Bearer blah";
  private ObjectMapper mapper = new ObjectMapper();
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  AuthControllerAdvice authControllerAdvice;

  @MockBean
  ChannelRepository channelRepository;

  @MockBean
  MessageRepository messageRepository;

  @Test
  public void test_getUnauthorizedResponse() throws Exception {
    mockMvc
        .perform(
            get("/api/members/channels").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().is(401));
  }

  @Test
  public void test_getMembers() throws Exception {
    List<Channel> expectedChannels = new ArrayList<Channel>();
    
    Channel c1 = new Channel("channelId1", "channelName1", "channelCreator1", true, true, new ArrayList<String>(), new ChannelTopic(), new ChannelPurpose() );
    Channel c2 = new Channel("channelId2", "channelName2", "channelCreator2", true, true, new ArrayList<String>(), new ChannelTopic(), new ChannelPurpose() );
    
    expectedChannels.add(c1);
    expectedChannels.add(c2);

    when(channelRepository.findAll()).thenReturn(expectedChannels);
    when(authControllerAdvice.getIsMember(exampleAuthToken)).thenReturn(true);

    MvcResult response = mockMvc
        .perform(
            get("/api/members/channels").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
        .andExpect(status().isOk()).andReturn();

    String responseString = response.getResponse().getContentAsString();
    List<Channel> channels = mapper.readValue(responseString, new TypeReference<List<Channel>>() {
    });

    assertEquals(expectedChannels, channels);

  }
}
