package edu.ucsb.mapache.documents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

public class MessageTests {

    @Test
    public void test_toString() {
        SlackUserProfile sup = new SlackUserProfile();
        Message m = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup);
        String expected = "Message{type='sampleType', subtype='sampleSubtype', ts='sampleTs', "
                + "user='sampleUser', text='sampleText', channel='sampleChannel', user_profile=" + sup + "}";
        assertEquals(expected, m.toString());
    }

    @Test
    public void test_getAndSetType() {
        Message m = new Message();
        m.setType("newType");
        assertEquals("newType", m.getType());
    }

    @Test
    public void test_getAndSetSubtype() {
        Message m = new Message();
        m.setSubtype("newSubtype");
        assertEquals("newSubtype", m.getSubtype());
    }

    @Test
    public void test_getAndSetTs() {
        Message m = new Message();
        m.setTs("newTs");
        assertEquals("newTs", m.getTs());
    }

    @Test
    public void test_getAndSetUser() {
        Message m = new Message();
        m.setUser("newUser");
        assertEquals("newUser", m.getUser());
    }

    @Test
    public void test_getAndSetText() {
        Message m = new Message();
        m.setText("newText");
        assertEquals("newText", m.getText());
    }

    @Test
    public void test_getAndSetChannel() {
        Message m = new Message();
        m.setChannel("newChannel");
        assertEquals("newChannel", m.getChannel());
    }

    @Test
    public void test_getAndSetUser_profile() {
        Message m = new Message();
        SlackUserProfile newProfile = new SlackUserProfile("test@test.com", "real", "display", "name");
        m.setUser_profile(newProfile);
        assertEquals(newProfile, m.getUser_profile());
    }

    @Test
    public void test_hashCode_matchesOnSameContent() {
        SlackUserProfile sup = new SlackUserProfile();
        Message m1 = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup);
        Message m2 = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup);
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    public void test_notEqualNull() {
        SlackUserProfile sup = new SlackUserProfile();
        Message m = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup);
        assertNotEquals(m, null);
    }

    @Test
    public void test_notEqualDifferentClass() {
        SlackUserProfile sup = new SlackUserProfile();
        Message m = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup);
        Assertions.assertFalse(m.equals(new Object()));
    }

    @Test
    public void test_equalsCopy() {
        SlackUserProfile sup = new SlackUserProfile();
        Message m1 = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup);
        Message m2 = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup);
        assertTrue(m1.equals(m2));
    }

    @Test
    public void testAdmin_equalsSelf() {
        SlackUserProfile sup = new SlackUserProfile();
        Message m1 = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup);
        assertTrue(m1.equals(m1));
    }

}
