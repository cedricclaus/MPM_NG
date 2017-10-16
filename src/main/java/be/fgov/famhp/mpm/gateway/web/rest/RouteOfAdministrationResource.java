package be.fgov.famhp.mpm.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.fgov.famhp.mpm.gateway.domain.RouteOfAdministration;
import be.fgov.famhp.mpm.gateway.service.RouteOfAdministrationService;
import be.fgov.famhp.mpm.gateway.web.rest.util.HeaderUtil;
import be.fgov.famhp.mpm.gateway.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RouteOfAdministration.
 */
@RestController
@RequestMapping("/api")
public class RouteOfAdministrationResource {

    private final Logger log = LoggerFactory.getLogger(RouteOfAdministrationResource.class);

    private static final String ENTITY_NAME = "routeOfAdministration";

    private final RouteOfAdministrationService routeOfAdministrationService;

    public RouteOfAdministrationResource(RouteOfAdministrationService routeOfAdministrationService) {
        this.routeOfAdministrationService = routeOfAdministrationService;
    }

    /**
     * POST  /route-of-administrations : Create a new routeOfAdministration.
     *
     * @param routeOfAdministration the routeOfAdministration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new routeOfAdministration, or with status 400 (Bad Request) if the routeOfAdministration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/route-of-administrations")
    @Timed
    public ResponseEntity<RouteOfAdministration> createRouteOfAdministration(@Valid @RequestBody RouteOfAdministration routeOfAdministration) throws URISyntaxException {
        log.debug("REST request to save RouteOfAdministration : {}", routeOfAdministration);
        if (routeOfAdministration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new routeOfAdministration cannot already have an ID")).body(null);
        }
        RouteOfAdministration result = routeOfAdministrationService.save(routeOfAdministration);
        return ResponseEntity.created(new URI("/api/route-of-administrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /route-of-administrations : Updates an existing routeOfAdministration.
     *
     * @param routeOfAdministration the routeOfAdministration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated routeOfAdministration,
     * or with status 400 (Bad Request) if the routeOfAdministration is not valid,
     * or with status 500 (Internal Server Error) if the routeOfAdministration couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/route-of-administrations")
    @Timed
    public ResponseEntity<RouteOfAdministration> updateRouteOfAdministration(@Valid @RequestBody RouteOfAdministration routeOfAdministration) throws URISyntaxException {
        log.debug("REST request to update RouteOfAdministration : {}", routeOfAdministration);
        if (routeOfAdministration.getId() == null) {
            return createRouteOfAdministration(routeOfAdministration);
        }
        RouteOfAdministration result = routeOfAdministrationService.save(routeOfAdministration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, routeOfAdministration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /route-of-administrations : get all the routeOfAdministrations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of routeOfAdministrations in body
     */
    @GetMapping("/route-of-administrations")
    @Timed
    public ResponseEntity<List<RouteOfAdministration>> getAllRouteOfAdministrations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RouteOfAdministrations");
        Page<RouteOfAdministration> page = routeOfAdministrationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/route-of-administrations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /route-of-administrations/:id : get the "id" routeOfAdministration.
     *
     * @param id the id of the routeOfAdministration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the routeOfAdministration, or with status 404 (Not Found)
     */
    @GetMapping("/route-of-administrations/{id}")
    @Timed
    public ResponseEntity<RouteOfAdministration> getRouteOfAdministration(@PathVariable String id) {
        log.debug("REST request to get RouteOfAdministration : {}", id);
        RouteOfAdministration routeOfAdministration = routeOfAdministrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(routeOfAdministration));
    }

    /**
     * DELETE  /route-of-administrations/:id : delete the "id" routeOfAdministration.
     *
     * @param id the id of the routeOfAdministration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/route-of-administrations/{id}")
    @Timed
    public ResponseEntity<Void> deleteRouteOfAdministration(@PathVariable String id) {
        log.debug("REST request to delete RouteOfAdministration : {}", id);
        routeOfAdministrationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
