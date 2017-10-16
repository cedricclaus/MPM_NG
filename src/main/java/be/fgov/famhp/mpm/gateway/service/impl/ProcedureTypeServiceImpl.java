package be.fgov.famhp.mpm.gateway.service.impl;

import be.fgov.famhp.mpm.gateway.service.ProcedureTypeService;
import be.fgov.famhp.mpm.gateway.domain.ProcedureType;
import be.fgov.famhp.mpm.gateway.repository.ProcedureTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing ProcedureType.
 */
@Service
public class ProcedureTypeServiceImpl implements ProcedureTypeService{

    private final Logger log = LoggerFactory.getLogger(ProcedureTypeServiceImpl.class);

    private final ProcedureTypeRepository procedureTypeRepository;

    public ProcedureTypeServiceImpl(ProcedureTypeRepository procedureTypeRepository) {
        this.procedureTypeRepository = procedureTypeRepository;
    }

    /**
     * Save a procedureType.
     *
     * @param procedureType the entity to save
     * @return the persisted entity
     */
    @Override
    public ProcedureType save(ProcedureType procedureType) {
        log.debug("Request to save ProcedureType : {}", procedureType);
        return procedureTypeRepository.save(procedureType);
    }

    /**
     *  Get all the procedureTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<ProcedureType> findAll(Pageable pageable) {
        log.debug("Request to get all ProcedureTypes");
        return procedureTypeRepository.findAll(pageable);
    }

    /**
     *  Get one procedureType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public ProcedureType findOne(String id) {
        log.debug("Request to get ProcedureType : {}", id);
        return procedureTypeRepository.findOne(id);
    }

    /**
     *  Delete the  procedureType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete ProcedureType : {}", id);
        procedureTypeRepository.delete(id);
    }
}
