package edu.ucsb.mapache.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import edu.ucsb.mapache.repositories.CounterRepository;

@SpringBootTest
public class CounterTests {

    @MockBean
    CounterRepository mockCounterRepository;
    
    @Test
    public void test_constructor() {
        Counter c = new Counter("foo",0,"a counter for testing");
        assertEquals("foo",c.getKey());
        assertEquals(0, c.getValue());
        assertEquals("a counter for testing", c.getDescription());
        assertEquals(null, c.getLastReset());
    }

    @Test
    public void test_increment() {
      Counter c = new Counter("foo",0,"a counter for testing");
      c.increment();
      assertEquals(1, c.getValue());
    }

    @Test
    public void test_reset() {
      Counter c = new Counter("foo", 1, "a counter for testing");
      c.increment();  c.increment();  c.increment();
      assertEquals(4, c.getValue());
      c.reset(1);
      assertEquals(1, c.getValue());
      assertNotNull(c.getLastReset());
    }


    @Test
    public void test_hours_diff() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        java.util.Date firstDate = sdf.parse("06/24/2017");
        java.util.Date secondDate = sdf.parse("06/26/2017");
        
        long diff = Counter.hoursDiff(firstDate, secondDate);
        assertEquals(48L,diff);
    }

    @Test
    public  void test_initializeCounter_whenCounterPresent () {
        Counter c = new Counter("foo",0,"a test counter called foo");
        Optional<Counter> opt = Optional.of(c);
        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        Counter.initializeCounter(mockCounterRepository, "foo", 0, "a test counter called foo");
    }

    @Test
    public  void test_initializeCounter_whenCounterNotPresent () {
        Optional<Counter> opt = Optional.empty();
        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        when(mockCounterRepository.save(any())).thenReturn(null);
        Counter.initializeCounter(mockCounterRepository, "foo", 0, "a test counter called foo");
        verify(mockCounterRepository, times(1)).save(any());
    }

    @Test
    public  void test_resetIfNeeded_whenCounterNotPresent () {
        Optional<Counter> opt = Optional.empty();
        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        Counter.resetIfNeeded(mockCounterRepository, "foo", 0, 24);
    }

    @Test
    public  void test_resetIfNeeded_whenCounterPresent_timeElapsed () throws ParseException {
        Counter c = new Counter("foo",0,"a test counter called foo");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        java.util.Date firstDate = sdf.parse("06/24/2017");
        c.setLastReset(firstDate);
        Optional<Counter> opt = Optional.of(c);
        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        Counter.resetIfNeeded(mockCounterRepository, "foo", 0, 24);
        verify(mockCounterRepository, times(1)).save(any());
    }


    @Test
    public  void test_resetIfNeeded_whenCounterPresent_timeNotElapsed () throws ParseException {
        Counter c = new Counter("foo",0,"a test counter called foo");
        java.util.Date now = new java.util.Date();
        c.setLastReset(now);
        Optional<Counter> opt = Optional.of(c);
        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        Counter.resetIfNeeded(mockCounterRepository, "foo", 0, 24);
        verify(mockCounterRepository, times(0)).save(any());

    }
   
    
}
