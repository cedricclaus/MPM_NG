package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.RouteOfAdministration;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the RouteOfAdministration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RouteOfAdministrationRepository extends MongoRepository<RouteOfAdministration, String> {

}
