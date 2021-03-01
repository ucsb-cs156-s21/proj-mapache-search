package edu.ucsb.mapache.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.ucsb.mapache.config.SecurityConfig;
import edu.ucsb.mapache.repositories.ChannelRepository;

import org.springframework.http.MediaType;

import java.io.IOException;

import edu.ucsb.mapache.services.GoogleSearchService;
import edu.ucsb.mapache.services.TeamEmailListService;


@WebMvcTest(value = SlackSlashCommandController.class)
@Import(SecurityConfig.class)
public class SlackSlashCommandControllerTests {
  
  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  SlackSlashCommandController slackSlashCommandController;

  @MockBean
  ChannelRepository channelRepository;


  @MockBean
  GoogleSearchService googleSearchService;

  @MockBean
  TeamEmailListService teamEmailListService;

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
        .andExpect(status().is(200));
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
        .andExpect(status().is(200));
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
        .andExpect(status().is(200));
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
        .andExpect(status().is(200));
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
        .andExpect(status().is(200));
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
        .andExpect(status().is(200));
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
        .andExpect(status().is(200));
  }


// google search test below
  @Test
  public void test_googleSearch() throws Exception {
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
        .param("text", "search google 0")
        .param("response_url", "value")
        )
        .andExpect(status().is(200));
  }

  @Test
  public void test_googleSearchNotSearchGoogle() throws Exception {
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
        .param("text", "notSeach notGoogle")
        .param("response_url", "value")
        )
        .andExpect(status().is(200));
  }

  @Test
  public void test_googleSearchMultipleArguments() throws Exception {
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
        .param("text", "search google two words")
        .param("response_url", "value")
        )
        .andExpect(status().is(200));
  }

  // WIP unable to test IOexception currently
  @Test
  public void test_googleSearch_1() throws Exception {
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
        .param("text", "search google")
        .param("response_url", "value")
        )
        .andExpect(status().is(200));
  }

  //testing google search

  @Test
  public void test_googleSearch_2() throws Exception {
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
        .param("text", "placeholder google")
        .param("response_url", "value")
        )
        .andExpect(status().is(200));
  }

  @Test
  public void test_googleSearch_3() throws Exception {
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
        .param("text", "search placeholder")
        .param("response_url", "value")
        )
        .andExpect(status().is(200));
  }

  @Test
  public void test_googleSearch_4() throws Exception {
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
        .param("text", "placeholder placeholder")
        .param("response_url", "value")
        )
        .andExpect(status().is(200));
  }

  @Test
  public void test_teamlistCommand() throws Exception {
    // fixes null error
    when(teamEmailListService.getEmailsStringFromTeamname("team")).thenReturn("email");
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
        .param("text", "teamlist team")
        .param("response_url", "value")
        )
        .andExpect(status().is(200));
  }
  @Test
  public void test_teamlistCommand_emptyTeamName() throws Exception {
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
        .param("text", "teamlist")
        .param("response_url", "value")
        )
        .andExpect(status().is(200));
  }

}

