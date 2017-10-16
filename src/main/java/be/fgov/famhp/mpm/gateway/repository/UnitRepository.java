package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.Unit;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Unit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitRepository extends MongoRepository<Unit, String> {

}
