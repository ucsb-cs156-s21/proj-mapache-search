package edu.ucsb.mapache.repositories;

import java.util.List;

import edu.ucsb.mapache.entities.Student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
  public List<Student> findAll();
  public List<Student> findByTeamName(String teamName);
}
