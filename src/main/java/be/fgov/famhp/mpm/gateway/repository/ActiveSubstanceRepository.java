package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.ActiveSubstance;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ActiveSubstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActiveSubstanceRepository extends MongoRepository<ActiveSubstance, String> {

}
