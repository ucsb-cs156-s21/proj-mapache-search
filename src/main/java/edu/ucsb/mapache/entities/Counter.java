package edu.ucsb.mapache.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ucsb.mapache.repositories.CounterRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Counter {

  private static Logger logger = LoggerFactory.getLogger(Counter.class);
  private static Map<String, String> descriptions = new HashMap<String, String>();

  static {
    descriptions.put("globalGoogleSearchApiTokenUsesToday",
        "Number of times app.google.search.apiToken has been used today");
  }

  @Id
  private String key;
  @Column(nullable = false)
  private int value;
  @Basic
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date lastReset;

  public Counter(String key, int value) {
    this.key = key;
    this.value = value;
    this.lastReset = null;
  }

  public void increment() {
    value++;
  }

  public void decrement() {
    value--;
  }

  public void reset(int value) {
    this.value = value;
    java.util.Date now = new java.util.Date();
    this.lastReset = now;
  }

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
  public static long hoursDiff(java.util.Date firstDate, java.util.Date secondDate) {
    long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
    long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    return diff;
  }

  public static Counter initializeCounter(CounterRepository counterRepository, String key, int value) {
    logger.info("Initializing key={} value={} description={}", key, value, Counter.getDescription(key));
    Optional<Counter> counter = counterRepository.findById(key);
    if (counter.isPresent()) {
      logger.info("key={} already present with value={}", key, counter.get().getValue());
      return counter.get();
    }
    java.util.Date now = new java.util.Date();
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

  public static Counter resetIfNeeded(CounterRepository counterRepository, String key, int value, long hours) {
    logger.info("resetIfNeeded key={} value={} hours={} description={}", key, value, hours, Counter.getDescription(key));

    Optional<Counter> counter = counterRepository.findById(key);
    Counter c = counter.orElse(null);
    if (c == null) {
      c = initializeCounter(counterRepository, key, value);
    }

    java.util.Date now = new java.util.Date();
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

  public static Counter resetOrDecrement(CounterRepository counterRepository, String key, int value, long hours) {
    logger.info("resetOrDecrement key={} value={} hours={} description={}", key, value, hours, Counter.getDescription(key));
    Counter c = resetIfNeeded(counterRepository, key, value, hours);
    c.decrement();
    counterRepository.save(c);
    return c;
  }

  public static String getDescription(String key) {
    return descriptions.get(key);
  }

}
