package edu.ucsb.mapache.repositories;

import edu.ucsb.mapache.documents.Message;
import edu.ucsb.mapache.entities.AppUser;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, ObjectId> {
    @Query("{ 'user_profile.real_name': ?0}")
    List<Message> findByUser(String searchUser);

    @Query("{ 'channel': ?0}")
    List<Message> findByChannel(String searchChannel);

    @Query("{$text: { $search: ?0 }}")
    List<Message> findByText(String searchString);

    @Query("{'reactions': {'$elemMatch': {'name' : ?0} }}")
    List<Message> findByReactionName(String emojiSymbol);
}

