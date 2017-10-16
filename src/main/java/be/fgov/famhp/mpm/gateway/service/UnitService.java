package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.Unit;
import be.fgov.famhp.mpm.gateway.repository.UnitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Unit.
 */
@Service
public class UnitService {

    private final Logger log = LoggerFactory.getLogger(UnitService.class);

    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    /**
     * Save a unit.
     *
     * @param unit the entity to save
     * @return the persisted entity
     */
    public Unit save(Unit unit) {
        log.debug("Request to save Unit : {}", unit);
        return unitRepository.save(unit);
    }

    /**
     *  Get all the units.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Unit> findAll(Pageable pageable) {
        log.debug("Request to get all Units");
        return unitRepository.findAll(pageable);
    }

    /**
     *  Get one unit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Unit findOne(String id) {
        log.debug("Request to get Unit : {}", id);
        return unitRepository.findOne(id);
    }

    /**
     *  Delete the  unit by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Unit : {}", id);
        unitRepository.delete(id);
    }
}
