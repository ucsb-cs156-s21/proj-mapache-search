package edu.ucsb.mapache.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import net.codebox.javabeantester.JavaBeanTester;

public class SearchTests {

  @Test
  public void testGettersAndSetters() throws Exception {
    // See: https://github.com/codebox/javabean-tester
    JavaBeanTester.test(Search.class);
  }

  @Test
  public void test_notEqualNull() throws Exception {
    Search s = new Search();
    assertNotEquals(s, null);
  }

  @Test
  public void test_notEqualAnotherClass() throws Exception {
    Search s = new Search();
    assertNotEquals(s, new Object());
    
  }

  @Test
  public void test_equalsSelf() throws Exception {
    Search s = new Search();
    assertEquals(s, s);
  }

  @Test
  public void test_equalsAnother() throws Exception {
   Search s1 = new Search(1L, "term", 1);
   Search s2 = new Search(1L, "term", 1);
    assertEquals(s1, s2);
  }

  @Test
  public void test_hashCode() throws Exception {
    Search s1 = new Search(1L, "term", 1);
   Search s2 = new Search(1L, "term", 1);
    assertEquals(s1.hashCode(), s2.hashCode());
  }

  @Test
  public void test_toString() throws Exception {
    Search s = new Search(1L, "term", 1);
    String expected = "{ id='1', searchTerm='term', count='1'}";
    assertEquals(expected, s.toString());
  }

}