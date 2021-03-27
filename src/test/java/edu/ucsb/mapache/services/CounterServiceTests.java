package edu.ucsb.mapache.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.nio.channels.NetworkChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ucsb.mapache.entities.Counter;
import edu.ucsb.mapache.repositories.CounterRepository;
import edu.ucsb.mapache.services.NowService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CounterServiceTests {

    @MockBean
    CounterRepository mockCounterRepository;

    @MockBean
    NowService nowService;

    @Autowired
    CounterService counterService;

    @Test
    public void test_hours_diff_48() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        java.util.Date firstDate = sdf.parse("06/24/2017");
        java.util.Date secondDate = sdf.parse("06/26/2017");

        long diff = counterService.hoursDiff(firstDate, secondDate);
        assertEquals(48L, diff);
    }

    @Test
    public void test_hours_diff_23() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        java.util.Date lessThanADayAgo = sdf.parse("06/24/2017 12:34");
        java.util.Date now = sdf.parse("06/25/2017 12:32");

        long diff = counterService.hoursDiff(lessThanADayAgo, now);
        assertEquals(23L, diff);
    }

    @Test
    public void test_initializeCounter_whenCounterPresent() throws ParseException {
        Counter c = new Counter("foo", 0);
        Optional<Counter> opt = Optional.of(c);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        java.util.Date now = sdf.parse("06/24/2017");

        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        when(nowService.now()).thenReturn(now);

        Counter cReturned = counterService.initializeCounter("foo", 0);
        assertEquals(c, cReturned);
    }

    @Test
    public void test_initializeCounter_whenCounterNotPresent() throws ParseException {
        Optional<Counter> opt = Optional.empty();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        java.util.Date now = sdf.parse("06/24/2017");
        when(nowService.now()).thenReturn(now);

        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        when(mockCounterRepository.save(any())).thenReturn(null);
        Counter cReturned = counterService.initializeCounter("foo", 0);

        Counter cExpected = new Counter("foo", 0);
        cExpected.setLastReset(now);
        assertEquals(cExpected, cReturned);
        verify(mockCounterRepository, times(1)).save(cExpected);
    }

    @Test
    public void test_resetIfNeeded_whenCounterNotPresent() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        java.util.Date now = sdf.parse("06/24/2017");
        when(nowService.now()).thenReturn(now);

        Optional<Counter> opt = Optional.empty();
        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        Counter cReturned = counterService.resetIfNeeded("foo", 0, 24);

        Counter cExpected = new Counter("foo", 0);
        cExpected.setLastReset(now);
        assertEquals(cExpected, cReturned);
        verify(mockCounterRepository, times(1)).save(cExpected);
    }

    @Test
    public void test_resetIfNeeded_whenCounterPresent_timeElapsed() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        java.util.Date aDayAgo = sdf.parse("06/24/2017 12:34");
        java.util.Date now = sdf.parse("06/25/2017 12:35");

        when(nowService.now()).thenReturn(now);

        Counter c = new Counter("foo", 0);
        c.setLastReset(aDayAgo);

        Optional<Counter> opt = Optional.of(c);
        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        Counter cReturned = counterService.resetIfNeeded("foo", 0, 24);
        Counter cExpected = new Counter("foo", 0);
        cExpected.setLastReset(now);

        assertEquals(cExpected, cReturned);
        verify(mockCounterRepository, times(1)).save(cExpected);
    }

    @Test
    public void test_resetIfNeeded_whenCounterPresent_timeNotElapsed() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        java.util.Date lessThanADayAgo = sdf.parse("06/24/2017 12:34");
        java.util.Date now = sdf.parse("06/25/2017 10:32");

        when(nowService.now()).thenReturn(now);

        Counter c = new Counter("foo", 0);
        c.setLastReset(lessThanADayAgo);

        Optional<Counter> opt = Optional.of(c);
        when(mockCounterRepository.findById("foo")).thenReturn(opt);
        Counter cReturned = counterService.resetIfNeeded("foo", 0, 24);
        Counter cExpected = new Counter("foo", 0);
        cExpected.setLastReset(lessThanADayAgo);

        assertEquals(cExpected, cReturned);
        verify(mockCounterRepository, times(0)).save(any());
    }

    @Test
    public void test_getCounterDescription() {
        String description = counterService.getDescription("globalGoogleSearchApiTokenUsesToday");
        String expected = "Number of times app.google.search.apiToken has been used today";
        assertEquals(expected, description);
    }

    @Test
    public void test_resetOrDecrement_whenCounterPresent_timeNotElapsed() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        java.util.Date lessThanADayAgo = sdf.parse("06/24/2017 12:34");
        java.util.Date now = sdf.parse("06/25/2017 12:32");
        when(nowService.now()).thenReturn(now);

        Counter c = new Counter("foo", 100);
        c.setLastReset(lessThanADayAgo);
        Optional<Counter> opt = Optional.of(c);
        when(mockCounterRepository.findById("foo")).thenReturn(opt);

        Counter cReturned = counterService.resetOrDecrement("foo", 100, 24);

        // See: https://stackoverflow.com/a/29169875 for explanation of ArgumentCaptor
        // Short version: it captures the value that was passed to the save method

        final ArgumentCaptor<Counter> captor = ArgumentCaptor.forClass(Counter.class);
        verify(mockCounterRepository).save(captor.capture());
        final Counter savedCounter = captor.getValue();

        Counter cExpected = new Counter("foo", 99);
        cExpected.setLastReset(lessThanADayAgo);
        assertEquals(cExpected, cReturned);
    }

    @Test
    public void test_reset() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        java.util.Date lessThanADayAgo = sdf.parse("06/24/2017 12:34");
        java.util.Date now = sdf.parse("06/25/2017 12:32");
        when(nowService.now()).thenReturn(now);

        Counter c = new Counter("foo", 1);
        c.increment();
        c.increment();
        c.increment();
        assertEquals(4, c.getValue());

        counterService.reset(c, 1);
        assertEquals(1, c.getValue());
        assertEquals(now,c.getLastReset());
    }
}
