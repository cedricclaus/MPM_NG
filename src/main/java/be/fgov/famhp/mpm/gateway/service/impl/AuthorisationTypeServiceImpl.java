package be.fgov.famhp.mpm.gateway.service.impl;

import be.fgov.famhp.mpm.gateway.service.AuthorisationTypeService;
import be.fgov.famhp.mpm.gateway.domain.AuthorisationType;
import be.fgov.famhp.mpm.gateway.repository.AuthorisationTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing AuthorisationType.
 */
@Service
public class AuthorisationTypeServiceImpl implements AuthorisationTypeService{

    private final Logger log = LoggerFactory.getLogger(AuthorisationTypeServiceImpl.class);

    private final AuthorisationTypeRepository authorisationTypeRepository;

    public AuthorisationTypeServiceImpl(AuthorisationTypeRepository authorisationTypeRepository) {
        this.authorisationTypeRepository = authorisationTypeRepository;
    }

    /**
     * Save a authorisationType.
     *
     * @param authorisationType the entity to save
     * @return the persisted entity
     */
    @Override
    public AuthorisationType save(AuthorisationType authorisationType) {
        log.debug("Request to save AuthorisationType : {}", authorisationType);
        return authorisationTypeRepository.save(authorisationType);
    }

    /**
     *  Get all the authorisationTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<AuthorisationType> findAll(Pageable pageable) {
        log.debug("Request to get all AuthorisationTypes");
        return authorisationTypeRepository.findAll(pageable);
    }

    /**
     *  Get one authorisationType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public AuthorisationType findOne(String id) {
        log.debug("Request to get AuthorisationType : {}", id);
        return authorisationTypeRepository.findOne(id);
    }

    /**
     *  Delete the  authorisationType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete AuthorisationType : {}", id);
        authorisationTypeRepository.delete(id);
    }
}
