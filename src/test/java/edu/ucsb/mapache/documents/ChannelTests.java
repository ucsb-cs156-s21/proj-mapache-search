package edu.ucsb.mapache.documents;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Test;

import net.codebox.javabeantester.JavaBeanTester;

public class ChannelTests {

    @Test
    public void test_toString_withPurposeAndTopic() {
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
    public void test_hashCode_matchesOnSameContent_withPurposeAndTopic() {
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
    public void test_notEqualDifferentClass_withNull() {
        Channel c1 = new Channel();
        Assertions.assertFalse(c1.equals(new Object()));
    }



  @Test
  public void testGettersAndSetters() throws Exception {
      // See: https://github.com/codebox/javabean-tester
      JavaBeanTester.test(Channel.class, "members");
  }

  @Test
  public void test_getAndSetMembers_withList() {
    Channel channel = new Channel();
    List<String> members = new ArrayList<String>();
    members.add("member1");
    channel.setMembers(members);
    assertEquals(members,channel.getMembers());
  }

 
  @Test
  public void test_toString() {
    Channel c = new Channel("channelId", "channelName", "channelCreator", true, true, new ArrayList<String>(), new ChannelTopic(), new ChannelPurpose() );
    String expected = "{ id='channelId', name='channelName', creator='channelCreator', is_archived='true', is_general='true', members='[]', topic='{ value='null', creator='null'}', purpose='{ value='null', creator='null'}'}";
    assertEquals(expected, c.toString());
  }


  @Test
  public void test_hashCode_matchesOnSameContent() {
    Channel c1 = new Channel("channelId", "channelName", "channelCreator", true, true, new ArrayList<String>(), new ChannelTopic(), new ChannelPurpose() );
    Channel c2 = new Channel("channelId", "channelName", "channelCreator", true, true, new ArrayList<String>(), new ChannelTopic(), new ChannelPurpose() );
    assertEquals(c1.hashCode(), c2.hashCode());
  }

  @Test
  public void test_notEqualDifferentClass() {
    Channel c1 = new Channel("channelId", "channelName", "channelCreator", true, true, new ArrayList<String>(), new ChannelTopic(), new ChannelPurpose() );
    assertFalse(c1.equals(new Object()));
  }

  @Test
  public void testAdmin_equalsCopy() {
    Channel c1 = new Channel("channelId", "channelName", "channelCreator", true, true, new ArrayList<String>(), new ChannelTopic(), new ChannelPurpose() );
    Channel c2 = new Channel("channelId", "channelName", "channelCreator", true, true, new ArrayList<String>(), new ChannelTopic(), new ChannelPurpose() );
    assertTrue(c1.equals(c2));
  }

  @Test
  public void testAdmin_equalsSelf() {
    Channel c1 = new Channel("channelId", "channelName", "channelCreator", true, true, new ArrayList<String>(), new ChannelTopic(), new ChannelPurpose() );
    assertTrue(c1.equals(c1));
  }


}

