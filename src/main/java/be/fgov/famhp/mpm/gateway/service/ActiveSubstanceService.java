package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.ActiveSubstance;
import be.fgov.famhp.mpm.gateway.repository.ActiveSubstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing ActiveSubstance.
 */
@Service
public class ActiveSubstanceService {

    private final Logger log = LoggerFactory.getLogger(ActiveSubstanceService.class);

    private final ActiveSubstanceRepository activeSubstanceRepository;

    public ActiveSubstanceService(ActiveSubstanceRepository activeSubstanceRepository) {
        this.activeSubstanceRepository = activeSubstanceRepository;
    }

    /**
     * Save a activeSubstance.
     *
     * @param activeSubstance the entity to save
     * @return the persisted entity
     */
    public ActiveSubstance save(ActiveSubstance activeSubstance) {
        log.debug("Request to save ActiveSubstance : {}", activeSubstance);
        return activeSubstanceRepository.save(activeSubstance);
    }

    /**
     *  Get all the activeSubstances.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<ActiveSubstance> findAll(Pageable pageable) {
        log.debug("Request to get all ActiveSubstances");
        return activeSubstanceRepository.findAll(pageable);
    }

    /**
     *  Get one activeSubstance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public ActiveSubstance findOne(String id) {
        log.debug("Request to get ActiveSubstance : {}", id);
        return activeSubstanceRepository.findOne(id);
    }

    /**
     *  Delete the  activeSubstance by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ActiveSubstance : {}", id);
        activeSubstanceRepository.delete(id);
    }
}
