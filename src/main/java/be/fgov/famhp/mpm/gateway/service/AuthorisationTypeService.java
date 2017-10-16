package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.AuthorisationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AuthorisationType.
 */
public interface AuthorisationTypeService {

    /**
     * Save a authorisationType.
     *
     * @param authorisationType the entity to save
     * @return the persisted entity
     */
    AuthorisationType save(AuthorisationType authorisationType);

    /**
     *  Get all the authorisationTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AuthorisationType> findAll(Pageable pageable);

    /**
     *  Get the "id" authorisationType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AuthorisationType findOne(String id);

    /**
     *  Delete the "id" authorisationType.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
