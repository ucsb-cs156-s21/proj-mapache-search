package edu.ucsb.mapache.documents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import net.codebox.javabeantester.JavaBeanTester;

public class ChannelPurposeTests {

    @Test
    public void testGettersAndSetters() throws Exception {
        // See: https://github.com/codebox/javabean-tester
        JavaBeanTester.test(ChannelPurpose.class);
    }

    @Test
  public void test_toString() {
    ChannelPurpose c = new ChannelPurpose("value", "creator");
    String expected = "{ value='value', creator='creator'}";
    assertEquals(expected, c.toString());
  }


  @Test
  public void test_hashCode_matchesOnSameContent() {
    ChannelPurpose cp1 = new ChannelPurpose("value", "creator");
    ChannelPurpose cp2 = new ChannelPurpose("value", "creator");
    assertEquals(cp1.hashCode(), cp2.hashCode());
  }

  @Test
  public void test_notEqualNull() {
    assertNotEquals(new ChannelPurpose(), null);
  }

  @Test
  public void test_notEqualDifferentClass() {
    ChannelPurpose cp1 = new ChannelPurpose("value", "creator");
    assertFalse(cp1.equals(new Object()));
  }

  @Test
  public void testAdmin_equalsCopy() {
    ChannelPurpose cp1 = new ChannelPurpose("value", "creator");
    ChannelPurpose cp2 = new ChannelPurpose("value", "creator");
    assertTrue(cp1.equals(cp2));
  }

  @Test
  public void testAdmin_equalsSelf() {
    ChannelPurpose cp1 = new ChannelPurpose("value", "creator");
    assertTrue(cp1.equals(cp1));
  }


}