package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.Authority;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Authority entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {
}
