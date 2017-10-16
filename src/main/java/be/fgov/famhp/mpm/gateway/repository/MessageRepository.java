package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.Message;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Message entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

}
