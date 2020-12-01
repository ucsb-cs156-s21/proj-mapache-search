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


@WebMvcTest(value = SlackSlashCommandController.class)
@Import(SecurityConfig.class)
public class SlackSlashCommandControllerTests {
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  ChannelRepository channelRepository;

  private final String URL="/api/public/slash-command";
  
  @Test
  public void test_postSlashMessage() throws Exception {
    mockMvc
        .perform(post(URL))
        .andExpect(status().is(200));
  }

}
