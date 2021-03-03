package edu.ucsb.mapache.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class StudentTests {
    @Test
  public void testSearch_getAndSetId() {
    Search s = new Search();
    s.setId(1);
    assertEquals(1, s.getId());
  }

  @Test
  public void testSearch_getAndSetSearchTerm() {
    Search s = new Search();
    s.setSearchTerm("term");
    assertEquals("term", s.getSearchTerm());
  }

  @Test
  public void testSearch_getAndSetCount() {
    Search s = new Search();
    s.setCount(123);
    assertEquals(123, s.getCount());
  }
}