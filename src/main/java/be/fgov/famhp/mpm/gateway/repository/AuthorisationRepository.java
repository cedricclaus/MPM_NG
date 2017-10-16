package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.Authorisation;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Authorisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorisationRepository extends MongoRepository<Authorisation, String> {

}
