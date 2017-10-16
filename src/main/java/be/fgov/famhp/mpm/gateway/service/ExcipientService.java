package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.Excipient;
import be.fgov.famhp.mpm.gateway.repository.ExcipientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Excipient.
 */
@Service
public class ExcipientService {

    private final Logger log = LoggerFactory.getLogger(ExcipientService.class);

    private final ExcipientRepository excipientRepository;

    public ExcipientService(ExcipientRepository excipientRepository) {
        this.excipientRepository = excipientRepository;
    }

    /**
     * Save a excipient.
     *
     * @param excipient the entity to save
     * @return the persisted entity
     */
    public Excipient save(Excipient excipient) {
        log.debug("Request to save Excipient : {}", excipient);
        return excipientRepository.save(excipient);
    }

    /**
     *  Get all the excipients.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Excipient> findAll(Pageable pageable) {
        log.debug("Request to get all Excipients");
        return excipientRepository.findAll(pageable);
    }

    /**
     *  Get one excipient by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Excipient findOne(String id) {
        log.debug("Request to get Excipient : {}", id);
        return excipientRepository.findOne(id);
    }

    /**
     *  Delete the  excipient by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Excipient : {}", id);
        excipientRepository.delete(id);
    }
}
