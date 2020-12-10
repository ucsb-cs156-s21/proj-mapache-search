package edu.ucsb.mapache.documents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
public class MessageTests {

    @Test
    public void test_toString() {
        SlackUserProfile sup = new SlackUserProfile();
        MessageReactions mr = new MessageReactions();
        List<MessageReactions> mr1 = new ArrayList<MessageReactions>();
        mr1.add(mr); 
        Message m = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup, mr1);
        String expected = "Message{type='sampleType', subtype='sampleSubtype', ts='sampleTs', "
                + "user='sampleUser', text='sampleText', channel='sampleChannel', user_profile=" + sup + mr1 + "}";
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
    public void test_getAndSetMessage_reactions() {
        Message m = new Message();
        MessageReactions mr = new MessageReactions(5, "name");
        List<MessageReactions> mr1 = new ArrayList<MessageReactions>();
        mr1.add(mr); 
        m.setMessage_reactions(mr1);
        assertEquals(mr, m.getMessage_reactions());
    }

    @Test
    public void test_hashCode_matchesOnSameContent() {
        SlackUserProfile sup = new SlackUserProfile();
        MessageReactions mr = new MessageReactions();
        List<MessageReactions> mr1 = new ArrayList<MessageReactions>();
        mr1.add(mr); 
        Message m1 = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup, mr1);
        Message m2 = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup, mr1);
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    public void test_notEqualNull() {
        SlackUserProfile sup = new SlackUserProfile();
        MessageReactions mr = new MessageReactions();
        List<MessageReactions> mr1 = new ArrayList<MessageReactions>();
        mr1.add(mr); 
        Message m = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup, mr1);
        assertNotEquals(m, null);
    }

    @Test
    public void test_notEqualDifferentClass() {
        SlackUserProfile sup = new SlackUserProfile();
        MessageReactions mr = new MessageReactions();
        List<MessageReactions> mr1 = new ArrayList<MessageReactions>();
        mr1.add(mr); 
        Message m = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup, mr1);
        Assertions.assertFalse(m.equals(new Object()));
    }

    @Test
    public void test_equalsCopy() {
        SlackUserProfile sup = new SlackUserProfile();
        MessageReactions mr = new MessageReactions();
        List<MessageReactions> mr1 = new ArrayList<MessageReactions>();
        mr1.add(mr); 
        Message m1 = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup, mr1);
        Message m2 = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup, mr1);
        assertTrue(m1.equals(m2));
    }

    @Test
    public void testAdmin_equalsSelf() {
        SlackUserProfile sup = new SlackUserProfile();
        MessageReactions mr = new MessageReactions();
        List<MessageReactions> mr1 = new ArrayList<MessageReactions>();
        mr1.add(mr); 
        Message m1 = new Message("sampleType", "sampleSubtype", "sampleTs", "sampleUser", "sampleText", "sampleChannel",
                sup, mr1);
        assertTrue(m1.equals(m1));
    }

}
