package edu.ucsb.mapache.repositories;

import edu.ucsb.mapache.entities.Searches;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface SearchRepository extends CrudRepository<Searches, Long> {
    public List<Searches> findAll();
}