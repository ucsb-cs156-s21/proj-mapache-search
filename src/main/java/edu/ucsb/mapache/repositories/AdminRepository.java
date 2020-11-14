package edu.ucsb.mapache.repositories;

import edu.ucsb.mapache.entities.Admin;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
  public List<Admin> findByEmail(String email);
}
