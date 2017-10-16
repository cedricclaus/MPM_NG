package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.AuthorisationStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the AuthorisationStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorisationStatusRepository extends MongoRepository<AuthorisationStatus, String> {

}
