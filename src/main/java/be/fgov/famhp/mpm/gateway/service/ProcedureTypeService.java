package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.ProcedureType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ProcedureType.
 */
public interface ProcedureTypeService {

    /**
     * Save a procedureType.
     *
     * @param procedureType the entity to save
     * @return the persisted entity
     */
    ProcedureType save(ProcedureType procedureType);

    /**
     *  Get all the procedureTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProcedureType> findAll(Pageable pageable);

    /**
     *  Get the "id" procedureType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProcedureType findOne(String id);

    /**
     *  Delete the "id" procedureType.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
