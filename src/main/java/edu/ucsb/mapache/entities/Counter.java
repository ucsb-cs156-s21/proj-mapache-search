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
import org.springframework.beans.factory.annotation.Autowired;

import edu.ucsb.mapache.repositories.CounterRepository;
import edu.ucsb.mapache.services.NowService;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Counter {



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

  
}
