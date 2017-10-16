package be.fgov.famhp.mpm.gateway.service.impl;

import be.fgov.famhp.mpm.gateway.service.AuthorisationStatusService;
import be.fgov.famhp.mpm.gateway.domain.AuthorisationStatus;
import be.fgov.famhp.mpm.gateway.repository.AuthorisationStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing AuthorisationStatus.
 */
@Service
public class AuthorisationStatusServiceImpl implements AuthorisationStatusService{

    private final Logger log = LoggerFactory.getLogger(AuthorisationStatusServiceImpl.class);

    private final AuthorisationStatusRepository authorisationStatusRepository;

    public AuthorisationStatusServiceImpl(AuthorisationStatusRepository authorisationStatusRepository) {
        this.authorisationStatusRepository = authorisationStatusRepository;
    }

    /**
     * Save a authorisationStatus.
     *
     * @param authorisationStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public AuthorisationStatus save(AuthorisationStatus authorisationStatus) {
        log.debug("Request to save AuthorisationStatus : {}", authorisationStatus);
        return authorisationStatusRepository.save(authorisationStatus);
    }

    /**
     *  Get all the authorisationStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<AuthorisationStatus> findAll(Pageable pageable) {
        log.debug("Request to get all AuthorisationStatuses");
        return authorisationStatusRepository.findAll(pageable);
    }

    /**
     *  Get one authorisationStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public AuthorisationStatus findOne(String id) {
        log.debug("Request to get AuthorisationStatus : {}", id);
        return authorisationStatusRepository.findOne(id);
    }

    /**
     *  Delete the  authorisationStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete AuthorisationStatus : {}", id);
        authorisationStatusRepository.delete(id);
    }
}
