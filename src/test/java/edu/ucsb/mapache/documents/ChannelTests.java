package edu.ucsb.mapache.documents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import net.codebox.javabeantester.JavaBeanTester;

public class ChannelTests {
    @Test
    public void test_toString() {
        ChannelTopic ct = new ChannelTopic();
        ChannelPurpose cp = new ChannelPurpose();
        Channel c = new Channel("sampleId", "sampleName", "sampleCreator", false, false, new ArrayList<String>(), ct,cp);
        String expected = "{ id='sampleId', name='sampleName', creator='sampleCreator', is_archived='false'" +
            ", is_general='false', members='[]', topic='" + ct + "', purpose='" + cp + "'}";
        assertEquals(expected, c.toString());
    }

    @Test
    public void test_getAndSetId() {
        Channel c = new Channel();
        c.setId("sampleId");
        assertEquals("sampleId", c.getId());
    }

    @Test
    public void test_getAndSetName() {
        Channel c = new Channel();
        c.setName("sampleName");
        assertEquals("sampleName", c.getName());
    }

    @Test
    public void test_getAndSetCreator() {
        Channel c = new Channel();
        c.setCreator("sampleCreator");
        assertEquals("sampleCreator", c.getCreator());
    }

    @Test
    public void test_getAndSetIs_archived() {
        Channel c = new Channel();
        c.setIs_archived(true);
        assertEquals(true, c.getIs_archived());
    }

    @Test
    public void test_getAndSetIs_general() {
        Channel c = new Channel();
        c.setIs_general(true);
        assertEquals(true, c.getIs_general());
    }

    @Test
    public void test_getAndSetMembers() {
        ArrayList<String> members = new ArrayList<String>();
        members.add("Test Member");
        Channel c = new Channel();
        c.setMembers(members);
        assertEquals(members, c.getMembers());
    }

    @Test
    public void test_getAndSetTopic() {
        ChannelTopic ct = new ChannelTopic();
        Channel c = new Channel();
        c.setTopic(ct);
        assertEquals(ct, c.getTopic());
    }

    @Test
    public void test_getAndSetPurpose() {
        ChannelPurpose cp = new ChannelPurpose();
        Channel c = new Channel();
        c.setPurpose(cp);
        assertEquals(cp, c.getPurpose());
    }

    @Test
    public void test_IsIs_archived() {
        Channel c = new Channel();
        c.setIs_archived(true);
        assertTrue(c.isIs_archived());
    }

    @Test
    public void test_IsIs_general() {
        Channel c = new Channel();
        c.setIs_general(true);
        assertTrue(c.isIs_general());
    }

    @Test
    public void test_hashCode_matchesOnSameContent() {
        ChannelTopic ct = new ChannelTopic();
        ChannelPurpose cp = new ChannelPurpose();
        Channel c1 = new Channel("sampleId", "sampleName", "sampleCreator", false, false, new ArrayList<String>(), ct,cp);
        Channel c2 = new Channel("sampleId", "sampleName", "sampleCreator", false, false, new ArrayList<String>(), ct,cp);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    public void test_notEqualNull() {
        assertNotEquals(new Channel(), null);
    }

    @Test
    public void test_notEqualDifferentClass() {
        Channel c1 = new Channel();
        Assertions.assertFalse(c1.equals(new Object()));
    }

    @Test
    public void testPurpose_equalsCopy() {
        ChannelTopic ct = new ChannelTopic();
        ChannelPurpose cp = new ChannelPurpose();
        Channel c1 = new Channel("sampleId", "sampleName", "sampleCreator", false, false, new ArrayList<String>(), ct,cp);
        Channel c2 = new Channel("sampleId", "sampleName", "sampleCreator", false, false, new ArrayList<String>(), ct,cp);
        assertTrue(c1.equals(c2));
    }

    @Test
    public void testPurpose_equalsSelf() {
        ChannelTopic ct = new ChannelTopic();
        ChannelPurpose cp = new ChannelPurpose();
        Channel c1 = new Channel("sampleId", "sampleName", "sampleCreator", false, false, new ArrayList<String>(), ct, cp);
        assertTrue(c1.equals(c1));
    }

}

