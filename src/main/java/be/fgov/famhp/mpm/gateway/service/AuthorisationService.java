package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.Authorisation;
import be.fgov.famhp.mpm.gateway.repository.AuthorisationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Authorisation.
 */
@Service
public class AuthorisationService {

    private final Logger log = LoggerFactory.getLogger(AuthorisationService.class);

    private final AuthorisationRepository authorisationRepository;

    public AuthorisationService(AuthorisationRepository authorisationRepository) {
        this.authorisationRepository = authorisationRepository;
    }

    /**
     * Save a authorisation.
     *
     * @param authorisation the entity to save
     * @return the persisted entity
     */
    public Authorisation save(Authorisation authorisation) {
        log.debug("Request to save Authorisation : {}", authorisation);
        return authorisationRepository.save(authorisation);
    }

    /**
     *  Get all the authorisations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Authorisation> findAll(Pageable pageable) {
        log.debug("Request to get all Authorisations");
        return authorisationRepository.findAll(pageable);
    }

    /**
     *  Get one authorisation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Authorisation findOne(String id) {
        log.debug("Request to get Authorisation : {}", id);
        return authorisationRepository.findOne(id);
    }

    /**
     *  Delete the  authorisation by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Authorisation : {}", id);
        authorisationRepository.delete(id);
    }
}
