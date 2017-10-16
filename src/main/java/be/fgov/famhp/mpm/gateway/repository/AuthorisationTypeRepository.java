package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.AuthorisationType;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the AuthorisationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorisationTypeRepository extends MongoRepository<AuthorisationType, String> {

}
