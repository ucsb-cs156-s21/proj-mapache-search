package edu.ucsb.mapache.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.documents.Channel;
import edu.ucsb.mapache.documents.ChannelTopic;
import edu.ucsb.mapache.documents.ChannelPurpose;
import edu.ucsb.mapache.documents.SlackUserProfile;
import edu.ucsb.mapache.documents.Message;
import edu.ucsb.mapache.repositories.ChannelRepository;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import edu.ucsb.mapache.advice.AuthControllerAdvice;
import edu.ucsb.mapache.documents.Channel;
import edu.ucsb.mapache.documents.ChannelPurpose;
import edu.ucsb.mapache.documents.ChannelTopic;
import edu.ucsb.mapache.documents.SlackUser;
import edu.ucsb.mapache.repositories.ChannelRepository;
import edu.ucsb.mapache.repositories.SlackUserRepository;


@WebMvcTest(value = ChannelsController.class)
@WithMockUser
public class ChannelsControllerTests {

    private String exampleAuthToken = "token";

    private ObjectMapper mapper = new ObjectMapper();

  
    @MockBean
    AuthControllerAdvice authControllerAdvice;

    @MockBean
    ChannelRepository channelRepository;
  
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void test_get_channels_unauthorizedIfNotMember() throws Exception {
        mockMvc
            .perform(get("/api/members/channels").contentType("application/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
            .andExpect(status().is(401));
    }

    @Test
    public void test_get_channels_returnsListOfChannels() throws Exception {
        List<Channel> expectedChannels = new ArrayList<Channel>();
        expectedChannels.add(new Channel("id1", "name1", "creator1", true, true, new ArrayList<String>(),
                    new ChannelTopic(), new ChannelPurpose()));
        expectedChannels.add(new Channel("id2", "name2", "creator2", true, true, new ArrayList<String>(),
                    new ChannelTopic(), new ChannelPurpose()));
        when(channelRepository.findAll()).thenReturn(expectedChannels);
        when(authControllerAdvice.getIsMember(anyString())).thenReturn(true);
        MvcResult response = mockMvc
            .perform(get("/api/members/channels").contentType("appication/json").header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
            .andExpect(status().isOk()).andReturn();
        String responseString = response.getResponse().getContentAsString();
        List<Channel> channels = mapper.readValue(responseString, new TypeReference<List<Channel>>() {
        });
        assertEquals(expectedChannels, channels);
    }

    @Test
    public void test_get_messageOfChannel_unauthorizedIfNotMember() throws Exception {
        mockMvc
            .perform(get("/api/members/channel/test-channel/messages").contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
            .andExpect(status().is(401));
    }

    @Test
    public void test_get_messageOfChannel_returnsListOfMessages() throws Exception {
        List<Message> expectedMessages = new ArrayList<Message>();
        expectedMessages.add(new Message("type1", "subtype1", "ts1", "user1", "text1", "channel1", new SlackUserProfile()));
        expectedMessages.add(new Message("type2", "subtype2", "ts2", "user2", "text2", "channel2", new SlackUserProfile()));
        when(messageRepository.findByChannel(anyString())).thenReturn(expectedMessages);
        when(authControllerAdvice.getIsMember(anyString())).thenReturn(true);
        MvcResult response = mockMvc
            .perform(get("/api/members/channel/test-channel/messages").contentType("appication/json")
                    .header(HttpHeaders.AUTHORIZATION, exampleAuthToken))
            .andExpect(status().isOk()).andReturn();
        String responseString = response.getResponse().getContentAsString();
        List<Message> messages = mapper.readValue(responseString, new TypeReference<List<Message>>() {
        });
        assertEquals(expectedMessages, messages);
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
