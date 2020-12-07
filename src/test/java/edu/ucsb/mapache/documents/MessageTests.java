package edu.ucsb.mapache.documents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import net.codebox.javabeantester.JavaBeanTester;

public class MessageTests {

    @Test
    public void testGettersAndSetters() throws Exception {
        // See: https://github.com/codebox/javabean-tester
        JavaBeanTester.test(Message.class, "messages");
    }

    @Test
    public void test_getAndSetMembers() {
        Message message = new Message();
        String test_message = "test_message";
        message.setText(test_message);
        assertEquals(test_message, message.getText());
    }

    @Test
    public void test_toString() {
        Message m = new Message("messageId", "messageType", "messageSubtype", "messageTs", "messageUser", "messageText",
                "messageChannel");
        String expected = "{ id='messageId', type='messageType', subtype='messageSubtype', ts='messageTs', user='messageUser', text='messageText', channel='messageChannel'}";
        assertEquals(expected, m.toString());
    }

    @Test
    public void test_hashCode_matchesOnSameContent() {
        Message m1 = new Message("messageId", "messageType", "messageSubtype", "messageTs", "messageUser",
                "messageText", "messageChannel");
        Message m2 = new Message("messageId", "messageType", "messageSubtype", "messageTs", "messageUser",
                "messageText", "messageChannel");
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    public void test_notEqualNull() {
        assertNotEquals(new Message(), null);
    }

    @Test
    public void test_notEqualDifferentClass() {
        Message m = new Message("messageId", "messageType", "messageSubtype", "messageTs", "messageUser", "messageText",
                "messageChannel");
        assertFalse(m.equals(new Object()));
    }

    @Test
    public void test_equalsCopy() {
        Message m1 = new Message("messageId", "messageType", "messageSubtype", "messageTs", "messageUser",
                "messageText", "messageChannel");
        Message m2 = new Message("messageId", "messageType", "messageSubtype", "messageTs", "messageUser",
                "messageText", "messageChannel");
        assertTrue(m1.equals(m2));
    }

    @Test
    public void test_equalsSelf() {
        Message m = new Message("messageId", "messageType", "messageSubtype", "messageTs", "messageUser", "messageText",
                "messageChannel");
        assertTrue(m.equals(m));
    }

}
