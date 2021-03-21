package edu.ucsb.mapache.repositories;

import edu.ucsb.mapache.entities.Counter;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends CrudRepository<Counter, String> {
  public Optional<Counter> findById(String key);
}
