package edu.ucsb.mapache.documents;

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
  public void testGettersAndSetters() throws Exception {
      // See: https://github.com/codebox/javabean-tester
      JavaBeanTester.test(Channel.class, "members");
  }

  @Test
  public void test_getAndSetMembers() {
    Channel channel = new Channel();
    List<String> members = new ArrayList<String>();
    members.add("member1");
    channel.setMembers(members);
    assertEquals(members,channel.getMembers());
  }

 
  @Test
  public void test_toString() {

    // public Channel(String id, String name, String creator, Boolean is_archived, Boolean is_general, List<String> members, ChannelTopic topic, ChannelPurpose purpose) {

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
  public void test_notEqualNull() {
    assertNotEquals(new Channel(), null);
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