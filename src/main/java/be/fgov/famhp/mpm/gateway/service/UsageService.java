package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.Usage;
import be.fgov.famhp.mpm.gateway.repository.UsageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Usage.
 */
@Service
public class UsageService {

    private final Logger log = LoggerFactory.getLogger(UsageService.class);

    private final UsageRepository usageRepository;

    public UsageService(UsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

    /**
     * Save a usage.
     *
     * @param usage the entity to save
     * @return the persisted entity
     */
    public Usage save(Usage usage) {
        log.debug("Request to save Usage : {}", usage);
        return usageRepository.save(usage);
    }

    /**
     *  Get all the usages.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Usage> findAll(Pageable pageable) {
        log.debug("Request to get all Usages");
        return usageRepository.findAll(pageable);
    }

    /**
     *  Get one usage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Usage findOne(String id) {
        log.debug("Request to get Usage : {}", id);
        return usageRepository.findOne(id);
    }

    /**
     *  Delete the  usage by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Usage : {}", id);
        usageRepository.delete(id);
    }
}
