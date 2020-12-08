package edu.ucsb.mapache.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.ucsb.mapache.config.SecurityConfig;
import edu.ucsb.mapache.repositories.ChannelRepository;

import org.springframework.http.MediaType;


@WebMvcTest(value = SlackSlashCommandController.class)
@Import(SecurityConfig.class)
public class SlackSlashCommandControllerTests {
  
  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  SlackSlashCommandController slackSlashCommandController;

  @MockBean
  ChannelRepository channelRepository;

  private final String testURL="/api/public/slash-command";
  
  @Test
  public void test_postSlashMessage() throws Exception { 
    // content type: https://api.slack.com/interactivity/slash-commands
    mockMvc
        .perform(post(testURL).contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                
                .param("token", slackSlashCommandController.getSlackToken())

        .param("team_id", "value")
        .param("team_domain", "value")
        .param("channel_id", "value")
        .param("channel_name", "value")
        .param("user_id", "value")
        .param("user_name", "value")
        .param("command", "value")
        .param("text", "value")
        .param("response_url", "value")
        )
        .andExpect(status().is(401));
  }

  @Test
  public void test_badToken() throws Exception {
        // content type: https://api.slack.com/interactivity/slash-commands
    mockMvc
        .perform(post(testURL).contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                
                .param("token", "BADTOKEN")

        .param("team_id", "value")
        .param("team_domain", "value")
        .param("channel_id", "value")
        .param("channel_name", "value")
        .param("user_id", "value")
        .param("user_name", "value")
        .param("command", "value")
        .param("text", "value")
        .param("response_url", "value")
        )
        .andExpect(status().is(401));
  }

  @Test
  public void test_emptyCommand() throws Exception {
        // content type: https://api.slack.com/interactivity/slash-commands
    mockMvc
        .perform(post(testURL).contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                
                .param("token", slackSlashCommandController.getSlackToken())

        .param("team_id", "value")
        .param("team_domain", "value")
        .param("channel_id", "value")
        .param("channel_name", "value")
        .param("user_id", "value")
        .param("user_name", "value")
        .param("command", "value")
        .param("text", "")
        .param("response_url", "value")
        )
        .andExpect(status().is(401));
  }

  @Test
  public void test_emptyCommand2() throws Exception {
        // content type: https://api.slack.com/interactivity/slash-commands
    mockMvc
        .perform(post(testURL).contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                
                .param("token", slackSlashCommandController.getSlackToken())

        .param("team_id", "value")
        .param("team_domain", "value")
        .param("channel_id", "value")
        .param("channel_name", "value")
        .param("user_id", "value")
        .param("user_name", "value")
        .param("command", "value")
        .param("text", "             ")
        .param("response_url", "value")
        )
        .andExpect(status().is(401));
  }

  @Test
  public void test_statusCommand() throws Exception {
        // content type: https://api.slack.com/interactivity/slash-commands
    mockMvc
        .perform(post(testURL).contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                
                .param("token", slackSlashCommandController.getSlackToken())

        .param("team_id", "value")
        .param("team_domain", "value")
        .param("channel_id", "value")
        .param("channel_name", "value")
        .param("user_id", "value")
        .param("user_name", "value")
        .param("command", "/mapache")
        .param("text", "status")
        .param("response_url", "value")
        )
        .andExpect(status().is(401));
  }

  @Test
  public void test_timeCommand() throws Exception {
        // content type: https://api.slack.com/interactivity/slash-commands
    mockMvc
        .perform(post(testURL).contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                
                .param("token", slackSlashCommandController.getSlackToken())

        .param("team_id", "value")
        .param("team_domain", "value")
        .param("channel_id", "value")
        .param("channel_name", "value")
        .param("user_id", "value")
        .param("user_name", "value")
        .param("command", "/mapache")
        .param("text", "time")
        .param("response_url", "value")
        )
        .andExpect(status().is(401));
  }

  @Test
  public void test_debugCommand() throws Exception {
        // content type: https://api.slack.com/interactivity/slash-commands
    mockMvc
        .perform(post(testURL).contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                
                .param("token", slackSlashCommandController.getSlackToken())

        .param("team_id", "value")
        .param("team_domain", "value")
        .param("channel_id", "value")
        .param("channel_name", "value")
        .param("user_id", "value")
        .param("user_name", "value")
        .param("command", "/mapache")
        .param("text", "debug")
        .param("response_url", "value")
        )
        .andExpect(status().is(401));
  }

}