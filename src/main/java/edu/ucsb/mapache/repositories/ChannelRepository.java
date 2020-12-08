package edu.ucsb.mapache.repositories;

import edu.ucsb.mapache.documents.Channel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChannelRepository extends MongoRepository<Channel, ObjectId> {
    @Query("{ 'name': ?0}")
    List<Channel> findByName(String name);
}
