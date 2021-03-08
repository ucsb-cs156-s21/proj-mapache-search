package edu.ucsb.mapache.repositories;

import edu.ucsb.mapache.entities.Search;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface SearchRepository extends CrudRepository<Search, Long> {
    public List<Search> findAll();
    public List<Search> findBySearchTerm(String term);
}