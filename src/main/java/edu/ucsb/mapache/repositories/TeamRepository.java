package edu.ucsb.mapache.repositories;

import java.util.List;

import edu.ucsb.mapache.entities.Team;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
  public List<Team> findAll();
  public List<Team> findByTeamName(String teamName);
}
