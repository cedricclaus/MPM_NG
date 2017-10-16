package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.RouteOfAdministration;
import be.fgov.famhp.mpm.gateway.repository.RouteOfAdministrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing RouteOfAdministration.
 */
@Service
public class RouteOfAdministrationService {

    private final Logger log = LoggerFactory.getLogger(RouteOfAdministrationService.class);

    private final RouteOfAdministrationRepository routeOfAdministrationRepository;

    public RouteOfAdministrationService(RouteOfAdministrationRepository routeOfAdministrationRepository) {
        this.routeOfAdministrationRepository = routeOfAdministrationRepository;
    }

    /**
     * Save a routeOfAdministration.
     *
     * @param routeOfAdministration the entity to save
     * @return the persisted entity
     */
    public RouteOfAdministration save(RouteOfAdministration routeOfAdministration) {
        log.debug("Request to save RouteOfAdministration : {}", routeOfAdministration);
        return routeOfAdministrationRepository.save(routeOfAdministration);
    }

    /**
     *  Get all the routeOfAdministrations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<RouteOfAdministration> findAll(Pageable pageable) {
        log.debug("Request to get all RouteOfAdministrations");
        return routeOfAdministrationRepository.findAll(pageable);
    }

    /**
     *  Get one routeOfAdministration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public RouteOfAdministration findOne(String id) {
        log.debug("Request to get RouteOfAdministration : {}", id);
        return routeOfAdministrationRepository.findOne(id);
    }

    /**
     *  Delete the  routeOfAdministration by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete RouteOfAdministration : {}", id);
        routeOfAdministrationRepository.delete(id);
    }
}
