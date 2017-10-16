package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.AuthorisationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AuthorisationStatus.
 */
public interface AuthorisationStatusService {

    /**
     * Save a authorisationStatus.
     *
     * @param authorisationStatus the entity to save
     * @return the persisted entity
     */
    AuthorisationStatus save(AuthorisationStatus authorisationStatus);

    /**
     *  Get all the authorisationStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AuthorisationStatus> findAll(Pageable pageable);

    /**
     *  Get the "id" authorisationStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AuthorisationStatus findOne(String id);

    /**
     *  Delete the "id" authorisationStatus.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
