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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import edu.ucsb.mapache.repositories.CounterRepository;
import edu.ucsb.mapache.services.NowService;

@SpringBootTest
public class CounterTests {

    @MockBean
    CounterRepository mockCounterRepository;

    @Autowired
    NowService nowService;

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

   
}
