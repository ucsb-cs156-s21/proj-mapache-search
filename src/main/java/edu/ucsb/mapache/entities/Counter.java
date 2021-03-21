package edu.ucsb.mapache.entities;

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

  @Id
  private String key;
  @Column(nullable = false)
  private int value;
  @Column(nullable = true)
  private String description;
  @Basic
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date lastReset;

  public Counter(String key, int value, String description) {
    this.key = key;
    this.value = value;
    this.description = description;
    this.lastReset = null;
  }

  public void increment() {
    value++;
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

  public static void initializeCounter(CounterRepository counterRepository, String key, int value, String description) {
    logger.info("Initializing key={} value={} description={}", key, value, description);
    Optional<Counter> counter = counterRepository.findById(key);
    if (counter.isPresent()) {
      logger.info("key={} already present with value={}", key, counter.get().getValue());
      return;
    }
    java.util.Date now = new java.util.Date();
    Counter c = new Counter(key, value, description);
    c.setLastReset(now);
    counterRepository.save(c);
    logger.info("New record for counter key={} saved");
  }

  /**
   * Reset this counter to value shown if more than the number of hours indicated
   * has passed since last reset
   * 
   * @param key
   * @param value
   * @param description
   */

  public static void resetIfNeeded(CounterRepository counterRepository, String key, int value, long hours) {
    logger.info("Resetting if needed: key={} value={} hours={}", key, value, hours);

    Optional<Counter> counter = counterRepository.findById(key);
    if (!counter.isPresent()) {
      logger.error("Counter with key={} not found", key);
      return;
    }
    java.util.Date now = new java.util.Date();
    long diff = hoursDiff(now, counter.get().getLastReset());
    logger.info("Hours since last reset={}", diff);
    if (diff >= hours) {
      Counter c = counter.get();
      c.setValue(value);
      c.setLastReset(now);
      counterRepository.save(c);
      logger.info("Counter with key={} updated to value={}", key, value);
    }
  }

}
