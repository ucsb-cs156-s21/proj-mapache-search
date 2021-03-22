package edu.ucsb.mapache.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucsb.mapache.entities.Counter;
import edu.ucsb.mapache.repositories.CounterRepository;

/**
 * Services associated with Counter entities
 */
@Service
public class CounterService {

    private static Logger logger = LoggerFactory.getLogger(CounterService.class);
    private  Map<String, String> descriptions = new HashMap<String, String>();

    public CounterService () {
        descriptions.put("globalGoogleSearchApiTokenUsesToday",
            "Number of times app.google.search.apiToken has been used today");
    }

    @Autowired
    private NowService nowService;

    @Autowired
    private CounterRepository counterRepository;

    /**
     * Return the hours difference between two java.util.Date values. When the hours
     * are fractional, the floor is returned; since the use case for this is to tell
     * whether "at least", say 1 hour, 6 hours, or 24 hours have passed between the
     * two dates. https://www.baeldung.com/java-date-difference
     * 
     * @param firstDate
     * @param secondDate
     * @return secondDate - firstDate
     */

    public long hoursDiff(java.util.Date firstDate, java.util.Date secondDate) {
        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;
    }

    public  Counter initializeCounter(String key, int value) {
        logger.info("Initializing key={} value={} description={}", key, value, getDescription(key));
        Optional<Counter> counter = counterRepository.findById(key);
        if (counter.isPresent()) {
            logger.info("key={} already present with value={}", key, counter.get().getValue());
            return counter.get();
        }
        java.util.Date now = nowService.now();
        Counter c = new Counter(key, value);
        c.setLastReset(now);
        counterRepository.save(c);
        logger.info("New record for counter key={} saved");
        return c;
    }

  /**
   * Reset this counter to value shown if more than the number of hours indicated
   * has passed since last reset
   * 
   * @param key
   * @param value
   * @param description
   */

  public  Counter resetIfNeeded(String key, int value, long hours) {
    logger.info("resetIfNeeded key={} value={} hours={} description={}", key, value, hours, getDescription(key));

    Optional<Counter> counter = counterRepository.findById(key);
    Counter c = counter.orElse(null);
    if (c == null) {
      c = initializeCounter(key, value);
    }

    java.util.Date now = nowService.now();
    long diff = hoursDiff(now, c.getLastReset());
    logger.info("Hours since last reset={}", diff);
    if (diff >= hours) {
      c.setValue(value);
      c.setLastReset(now);
      counterRepository.save(c);
      logger.info("Counter with key={} updated to value={}", key, value);
    }
    return c;
  }

    /**
     * Reset this counter to value shown if more than the number of hours indicated
     * has passed since last reset; otherwise increment it by one.
     * 
     * @param key
     * @param value
     * @param description
     */

    /**
     * Reset this counter to value shown if more than the number of hours indicated
     * has passed since last reset; otherwise decrement it by one.
     * 
     * @param key
     * @param value
     * @param description
     */

    public  Counter resetOrDecrement(String key, int value, long hours) {
        logger.info("resetOrDecrement key={} value={} hours={} description={}", key, value, hours,
                getDescription(key));
        Counter c = resetIfNeeded(key, value, hours);
        c.decrement();
        counterRepository.save(c);
        return c;
    }

    public String getDescription(String key) {
        return descriptions.get(key);
    }

    public void reset(Counter c, int value) {
        c.setValue(value);
        java.util.Date now = nowService.now();
        c.setLastReset(now);
      }

}
