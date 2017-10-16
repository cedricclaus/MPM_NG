package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.PharmaceuticalForm;
import be.fgov.famhp.mpm.gateway.repository.PharmaceuticalFormRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing PharmaceuticalForm.
 */
@Service
public class PharmaceuticalFormService {

    private final Logger log = LoggerFactory.getLogger(PharmaceuticalFormService.class);

    private final PharmaceuticalFormRepository pharmaceuticalFormRepository;

    public PharmaceuticalFormService(PharmaceuticalFormRepository pharmaceuticalFormRepository) {
        this.pharmaceuticalFormRepository = pharmaceuticalFormRepository;
    }

    /**
     * Save a pharmaceuticalForm.
     *
     * @param pharmaceuticalForm the entity to save
     * @return the persisted entity
     */
    public PharmaceuticalForm save(PharmaceuticalForm pharmaceuticalForm) {
        log.debug("Request to save PharmaceuticalForm : {}", pharmaceuticalForm);
        return pharmaceuticalFormRepository.save(pharmaceuticalForm);
    }

    /**
     *  Get all the pharmaceuticalForms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<PharmaceuticalForm> findAll(Pageable pageable) {
        log.debug("Request to get all PharmaceuticalForms");
        return pharmaceuticalFormRepository.findAll(pageable);
    }

    /**
     *  Get one pharmaceuticalForm by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public PharmaceuticalForm findOne(String id) {
        log.debug("Request to get PharmaceuticalForm : {}", id);
        return pharmaceuticalFormRepository.findOne(id);
    }

    /**
     *  Delete the  pharmaceuticalForm by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete PharmaceuticalForm : {}", id);
        pharmaceuticalFormRepository.delete(id);
    }
}
