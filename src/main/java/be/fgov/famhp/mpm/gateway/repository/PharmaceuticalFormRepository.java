package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.PharmaceuticalForm;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the PharmaceuticalForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PharmaceuticalFormRepository extends MongoRepository<PharmaceuticalForm, String> {

}
