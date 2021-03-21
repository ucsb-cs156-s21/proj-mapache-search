package edu.ucsb.mapache.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import net.codebox.javabeantester.JavaBeanTester;

public class SearchTests {

  // @Test
  // public void testGettersAndSetters() throws Exception {
  //   // See: https://github.com/codebox/javabean-tester
  //   JavaBeanTester.test(Search.class);
  // }

  @Test
  public void testIncrement() throws Exception {
    Search s = new Search();
    assertEquals(0,s.getCount());
    s.incrementCount();
    assertEquals(1,s.getCount());
    s.incrementCount();
    assertEquals(2,s.getCount());
  }

  // @Test
  // public void test_notEqualNull() throws Exception {
  //   Search s = new Search();
  //   assertNotEquals(s, null);
  // }

  // @Test
  // public void test_notEqualAnotherClass() throws Exception {
  //   Search s = new Search();
  //   assertNotEquals(s, new Object());
    
  // }
  //  @Test
  // public void testSearch_equalsSelf_With_Comparison_to_Other_Search_Objects() throws Exception {
  //   Search s1 = new Search(1L, "email", 1);
  //   Search s2 = new Search(2L, "email", 2);
  //   Search s3 = new Search(3L, "email3", 3);
  //   Search s4 = new Search(1L, "email", 4);
  //   Search s5 = new Search(1L, "email5", 1);

  //   assertEquals(s1, s1);
  //   assertEquals(s2, s2);
  //   assertEquals(s3, s3);
  //   assertNotEquals(s1, s3);
  //   assertNotEquals(s1, s2);
  //   assertNotEquals(s2, s3);
  //   assertNotEquals(s1, s4);
  //   assertNotEquals(s1, s5);
  //   assertEquals(s1.getSearchTerm(), s2.getSearchTerm());
  //   assertNotEquals(s1.getSearchTerm(), s3.getSearchTerm());
  //   assertNotEquals(s1.getId(), s2.getId());
  //   assertNotEquals(s3.getCount(), s1.getCount());
  // }

  // @Test
  // public void test_equalsSelf() throws Exception {
  //   Search s = new Search();
  //   assertEquals(s, s);
  // }

  // @Test
  // public void test_equalsAnother() throws Exception {
  //  Search s1 = new Search(1L, "term", 1);
  //  Search s2 = new Search(1L, "term", 1);
  //   assertEquals(s1, s2);
  // }

  // @Test
  // public void test_hashCode() throws Exception {
  //   Search s1 = new Search(1L, "term", 1);
  //  Search s2 = new Search(1L, "term", 1);
  //   assertEquals(s1.hashCode(), s2.hashCode());
  // }

  // @Test
  // public void test_toString() throws Exception {
  //   Search s = new Search(1L, "term", 1);
  //   String expected = "{ id='1', searchTerm='term', count='1'}";
  //   assertEquals(expected, s.toString());
  // }

}