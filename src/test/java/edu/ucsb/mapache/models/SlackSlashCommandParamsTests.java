package edu.ucsb.mapache.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import net.codebox.javabeantester.JavaBeanTester;


public class SlackSlashCommandParamsTests {

    @Test
    public void testGettersAndSetters() throws Exception {
        // See: https://github.com/codebox/javabean-tester
        // The classes that should NOT be tested with the Bean are the
        // ones that set and get List<> instances
        JavaBeanTester.test(SlackSlashCommandParams.class,"textParts");
    }
  
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
        SlackSlashCommandParams params = new SlackSlashCommandParams();
        assertEquals(params, params);
    }

	@Test
    public void test_notEqualsToken() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        SlackSlashCommandParams params2 = new SlackSlashCommandParams();
        params1.setToken("token");
        params2.setToken("wrongToken");
        assertFalse(params1.equals(params2));
    }

    @Test
    public void test_notEqualsTeamID() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        SlackSlashCommandParams params2 = new SlackSlashCommandParams();
        params1.setTeamId("team Id");
        params2.setTeamId("wrong team Id");
        assertFalse(params1.equals(params2));
    }

    @Test
    public void test_notEqualsTeamDomain() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        SlackSlashCommandParams params2 = new SlackSlashCommandParams();
        params1.setTeamDomain("team domain");
        params2.setTeamDomain("wrong team domain");
        assertFalse(params1.equals(params2));
    }

    @Test
    public void test_notEqualsChannelId() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        SlackSlashCommandParams params2 = new SlackSlashCommandParams();
        params1.setChannelId("channel id");
        params2.setChannelId("wrong channel id");
        assertFalse(params1.equals(params2));
    }

    @Test
    public void test_notEqualsChannelName() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        SlackSlashCommandParams params2 = new SlackSlashCommandParams();
        params1.setChannelName("channel name");
        params2.setChannelName("wrong channel name");
        assertFalse(params1.equals(params2));
    }

     @Test
    public void test_notEqualsUserId() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        SlackSlashCommandParams params2 = new SlackSlashCommandParams();
        params1.setUserId("user id");
        params2.setUserId("wrong user id");
        assertFalse(params1.equals(params2));
    }

    @Test
    public void test_notEqualsUsername() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        SlackSlashCommandParams params2 = new SlackSlashCommandParams();
        params1.setUserName("user username");
        params2.setUserName("wrong username");
        assertFalse(params1.equals(params2));
    }

	@Test
    public void test_notEqualsCommand() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        SlackSlashCommandParams params2 = new SlackSlashCommandParams();
        params1.setCommand("command");
        params2.setCommand("wrong command");
        assertFalse(params1.equals(params2));
    }

    @Test
    public void test_notEqualsText() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        SlackSlashCommandParams params2 = new SlackSlashCommandParams();
        params1.setText("text");
        params2.setText("wrong text");
        assertFalse(params1.equals(params2));
    }

    @Test
    public void test_notEqualsResponseURL() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        SlackSlashCommandParams params2 = new SlackSlashCommandParams();
        params1.setResponseUrl("response URL");
        params2.setResponseUrl("wrong response URL");
        assertFalse(params1.equals(params2));
    }

    @Test
    public void test_equalsAnother() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        SlackSlashCommandParams params2 = new SlackSlashCommandParams();
        assertEquals(params1, params2);
    }

    @Test
    public void test_hashCode() throws Exception {
        SlackSlashCommandParams params1 = new SlackSlashCommandParams();
        SlackSlashCommandParams params2 = new SlackSlashCommandParams();
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
