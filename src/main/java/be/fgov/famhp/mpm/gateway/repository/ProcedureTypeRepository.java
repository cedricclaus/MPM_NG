package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.ProcedureType;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProcedureType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcedureTypeRepository extends MongoRepository<ProcedureType, String> {

}
