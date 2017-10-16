package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.Usage;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Usage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsageRepository extends MongoRepository<Usage, String> {

}
