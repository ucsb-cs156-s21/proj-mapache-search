// package edu.ucsb.mapache.listeners;




// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyInt;
// import static org.mockito.ArgumentMatchers.anyLong;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.ArgumentMatchers.eq;

// import java.nio.channels.NetworkChannel;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.Locale;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
// import org.mockito.ArgumentCaptor;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.junit4.SpringRunner;

// import edu.ucsb.mapache.entities.Counter;
// import edu.ucsb.mapache.repositories.CounterRepository;
// import edu.ucsb.mapache.services.CounterService;
// import edu.ucsb.mapache.services.NowService;

// @RunWith(SpringRunner.class)
// @SpringBootTest

// public class StartupListenerTests {

//     @MockBean
//     CounterService counterService;

//     @MockBean
//     NowService nowService;

//     @MockBean
//     CounterRepository mockCounterRepository;

//     @Autowired
//     StartupListener startupListener;

//     @Test
//     public void test_onApplicationEvent()  {
//         // when(counterService.initializeCounter(anyString(),anyInt()));
//         // when(counterService.resetIfNeeded(anyString(),anyInt(),anyLong()));
//         // startupListener.onApplicationEvent(null);
//     }

// }
