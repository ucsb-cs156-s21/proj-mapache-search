package edu.ucsb.mapache.documents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

public class ChannelTopicTests {

  @Test
  public void test_toString() {
    ChannelTopic ct = new ChannelTopic("sampleTopic", "sampleCreator");
    String expected = "{ value='sampleTopic', creator='sampleCreator'}";
    assertEquals(expected, ct.toString());
  }

  @Test
  public void test_getAndSetValue() {
    ChannelTopic ct = new ChannelTopic();
    ct.setValue("sampleTopic");
    assertEquals("sampleTopic", ct.getValue());  
  }

  @Test
  public void test_getAndSetCreator() {
    ChannelTopic ct = new ChannelTopic();
    ct.setCreator("sampleCreator");
    assertEquals("sampleCreator", ct.getCreator());  
  }

  @Test
  public void test_hashCode_matchesOnSameContent() {
    ChannelTopic ct1 = new ChannelTopic("sampleTopic", "sampleCreator");
    ChannelTopic ct2 = new ChannelTopic("sampleTopic", "sampleCreator");
    assertEquals(ct1.hashCode(), ct2.hashCode());
  }

  @Test
  public void test_notEqualNull() {
    assertNotEquals(new ChannelTopic(), null);
  }

  @Test
  public void test_notEqualDifferentClass() {
    ChannelTopic ct1 = new ChannelTopic("sampleTopic", "sampleCreator");
    Assertions.assertFalse(ct1.equals(new Object()));
  }

  @Test
  public void testAdmin_equalsCopy() {
    ChannelTopic ct1 = new ChannelTopic("sampleTopic", "sampleCreator");
    ChannelTopic ct2 = new ChannelTopic("sampleTopic", "sampleCreator");
    assertTrue(ct1.equals(ct2));
  }

  @Test
  public void testAdmin_equalsSelf() {
    ChannelTopic ct1 = new ChannelTopic("sampleTopic", "sampleCreator");
    assertTrue(ct1.equals(ct1));
  }

}
