package edu.ucsb.mapache.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import edu.ucsb.mapache.repositories.CounterRepository;

@SpringBootTest
public class CounterTests {

    @MockBean
    CounterRepository mockCounterRepository;
    
    @Test
    public void test_constructor() {
        Counter c = new Counter("foo",0);
        assertEquals("foo",c.getKey());
        assertEquals(0, c.getValue());
        assertEquals(null, c.getLastReset());
    }

    @Test
    public void test_increment() {
      Counter c = new Counter("foo",0);
      c.increment();
      assertEquals(1, c.getValue());
    }


    @Test
    public void test_decrement() {
      Counter c = new Counter("foo",100);
      c.decrement();
      assertEquals(99, c.getValue());
    }

    @Test
    public void test_reset() {
      Counter c = new Counter("foo", 1);
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
        Counter c = new Counter("foo",0);
        Optional<Counter> opt = Optional.of(c);
        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        Counter.initializeCounter(mockCounterRepository, "foo", 0);
    }

    @Test
    public  void test_initializeCounter_whenCounterNotPresent () {
        Optional<Counter> opt = Optional.empty();
        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        when(mockCounterRepository.save(any())).thenReturn(null);
        Counter.initializeCounter(mockCounterRepository, "foo", 0);
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
        Counter c = new Counter("foo",0);
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
        Counter c = new Counter("foo",0);
        java.util.Date now = new java.util.Date();
        c.setLastReset(now);
        Optional<Counter> opt = Optional.of(c);
        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        Counter.resetIfNeeded(mockCounterRepository, "foo", 0, 24);
        verify(mockCounterRepository, times(0)).save(any());

    }
   
    @Test
    public void test_getCounterDescription ()  {
        String description = Counter.getDescription("globalGoogleSearchApiTokenUsesToday");
        String expected = "Number of times app.google.search.apiToken has been used today";
        assertEquals(expected, description);
    }
   

    @Test
    public  void test_resetOrDecrement_whenCounterPresent_timeNotElapsed () throws ParseException {
        Counter c = new Counter("foo",100);
        java.util.Date now = new java.util.Date();
        c.setLastReset(now);
        Optional<Counter> opt = Optional.of(c);
        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        Counter.resetOrDecrement(mockCounterRepository, "foo", 0, 24);

        // See: https://stackoverflow.com/a/29169875 for explanation of ArgumentCaptor
        // Short version: it captures the value that was passed to the save method

        final ArgumentCaptor<Counter> captor = ArgumentCaptor.forClass(Counter.class);
        verify(mockCounterRepository).save(captor.capture());
        final Counter savedCounter = captor.getValue();

        assertEquals("foo", savedCounter.getKey());
        assertEquals(99, savedCounter.getValue());

    }
    
}
