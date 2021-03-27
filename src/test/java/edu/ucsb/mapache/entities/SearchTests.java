package edu.ucsb.mapache.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import net.codebox.javabeantester.JavaBeanTester;

public class SearchTests {


  @Test
  public void testIncrement() throws Exception {
    Search s = new Search();
    assertEquals(0,s.getCount());
    s.incrementCount();
    assertEquals(1,s.getCount());
    s.incrementCount();
    assertEquals(2,s.getCount());
  }

}
