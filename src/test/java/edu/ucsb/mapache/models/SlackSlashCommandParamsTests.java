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

import java.io.IOException;


@WebMvcTest(value = SlackSlashCommandController.class)
@Import(SecurityConfig.class)
public class SlackSlashCommandControllerTests {
  
    @Test
    public void test_getTextParts() {
        SlackSlashCommandParams params = new SlackSlashCommandParams();
        params.setText("a b   c  \t d");
        String[] result = params.getTextParts();
        assertEquals(4, result.length);
        assertEquals("a",result[0]);
        assertEquals("b",result[1]);
        assertEquals("c",result[2]);
        assertEquals("d",result[3]);
    }

    @Test
    public void test_equalsSelf() throws Exception {
        SlackSlashCommandParams params = new SlackSlashCommandParams(1,10,10,new ArrayList<Course>());
        assertEquals(params, params);
    }

    @Test
    public void test_equalsAnother() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams(1,10,10,new ArrayList<Course>());
        SlackSlashCommandParams params2 = new SlackSlashCommandParams(1,10,10,new ArrayList<Course>());
        assertEquals(params1, params2);
    }

    @Test
    public void test_hashCode() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams(1,10,10,new ArrayList<Course>());
        SlackSlashCommandParams params2 = new SlackSlashCommandParams(1,10,10,new ArrayList<Course>());
        assertEquals(params1.hashCode(), params2.hashCode());
    }

    @Test
    public void test_toString() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        assertEquals("{ token='null', teamId='null', teamDomain='null', channelId='null', channelName='null', userId='null', userName='null', command='null', text='null', responseUrl='null'}", 
                    params1.toString());
    }

    @Test
    public void test_notEqualsRandomObject() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        Object o = new Object();
        assertFalse(params1.equals(o));
    }
}

