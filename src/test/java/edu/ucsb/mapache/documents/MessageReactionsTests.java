package edu.ucsb.mapache.documents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

public class MessageReactionsTests {

    @Test
    public void test_toString() {
        MessageReactions mr = new MessageReactions("sampleName", 1);
        String expected = "SlackUserProfile{" + "count='" + 1 + '\'' + ", name='" + "sampleName" + '\'' + '}';
        assertEquals(expected, mr.toString());
    }

    @Test
    public void test_getAndSetCount() {
        MessageReactions mr = new MessageReactions();
        mr.setCount(2);
        assertEquals(2, mr.getCount());
    }

    @Test
    public void test_getAndSetName() {
        MessageReactions mr = new MessageReactions();
        mr.setName("sampleName");
        assertEquals("sampleName", mr.getName());
    }

    @Test
    public void test_hashCode_matchesOnSameContent() {
        MessageReactions mr1 = new MessageReactions("sampleName", 1);
        MessageReactions mr2 = new MessageReactions("sampleName", 1);
        assertEquals(mr1.hashCode(), mr2.hashCode());
    }

    @Test
    public void test_notEqualNull() {
        MessageReactions mr = new MessageReactions("sampleName", 1);
        assertNotEquals(mr, null);
    }

    @Test
    public void test_notEqualDifferentClass() {
        MessageReactions mr = new MessageReactions("sampleName", 1);
        Assertions.assertFalse(mr.equals(new Object()));
    }

    @Test
    public void test_equalsCopy() {
        MessageReactions mr1 = new MessageReactions("sampleName", 1);
        MessageReactions mr2 = new MessageReactions("sampleName", 1);
        assertTrue(mr1.equals(mr2));
    }

    @Test
    public void testAdmin_equalsSelf() {
        MessageReactions mr = new MessageReactions("sampleName", 1);
        Assertions.assertFalse(mr.equals(mr));
    }

}
