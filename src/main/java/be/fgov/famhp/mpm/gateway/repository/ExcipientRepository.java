package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.Excipient;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Excipient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExcipientRepository extends MongoRepository<Excipient, String> {

}
