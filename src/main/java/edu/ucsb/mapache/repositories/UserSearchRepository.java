package edu.ucsb.mapache.repositories;

import edu.ucsb.mapache.entities.UserSearch;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface UserSearchRepository extends CrudRepository<UserSearch, Long> {
  public List<UserSearch> findAll();
  public List<UserSearch> findbyUserid(String userid);
}
