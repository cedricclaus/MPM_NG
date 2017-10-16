package be.fgov.famhp.mpm.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.fgov.famhp.mpm.gateway.domain.ActiveSubstance;
import be.fgov.famhp.mpm.gateway.service.ActiveSubstanceService;
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
 * REST controller for managing ActiveSubstance.
 */
@RestController
@RequestMapping("/api")
public class ActiveSubstanceResource {

    private final Logger log = LoggerFactory.getLogger(ActiveSubstanceResource.class);

    private static final String ENTITY_NAME = "activeSubstance";

    private final ActiveSubstanceService activeSubstanceService;

    public ActiveSubstanceResource(ActiveSubstanceService activeSubstanceService) {
        this.activeSubstanceService = activeSubstanceService;
    }

    /**
     * POST  /active-substances : Create a new activeSubstance.
     *
     * @param activeSubstance the activeSubstance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activeSubstance, or with status 400 (Bad Request) if the activeSubstance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/active-substances")
    @Timed
    public ResponseEntity<ActiveSubstance> createActiveSubstance(@Valid @RequestBody ActiveSubstance activeSubstance) throws URISyntaxException {
        log.debug("REST request to save ActiveSubstance : {}", activeSubstance);
        if (activeSubstance.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new activeSubstance cannot already have an ID")).body(null);
        }
        ActiveSubstance result = activeSubstanceService.save(activeSubstance);
        return ResponseEntity.created(new URI("/api/active-substances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /active-substances : Updates an existing activeSubstance.
     *
     * @param activeSubstance the activeSubstance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activeSubstance,
     * or with status 400 (Bad Request) if the activeSubstance is not valid,
     * or with status 500 (Internal Server Error) if the activeSubstance couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/active-substances")
    @Timed
    public ResponseEntity<ActiveSubstance> updateActiveSubstance(@Valid @RequestBody ActiveSubstance activeSubstance) throws URISyntaxException {
        log.debug("REST request to update ActiveSubstance : {}", activeSubstance);
        if (activeSubstance.getId() == null) {
            return createActiveSubstance(activeSubstance);
        }
        ActiveSubstance result = activeSubstanceService.save(activeSubstance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activeSubstance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /active-substances : get all the activeSubstances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of activeSubstances in body
     */
    @GetMapping("/active-substances")
    @Timed
    public ResponseEntity<List<ActiveSubstance>> getAllActiveSubstances(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ActiveSubstances");
        Page<ActiveSubstance> page = activeSubstanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/active-substances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /active-substances/:id : get the "id" activeSubstance.
     *
     * @param id the id of the activeSubstance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activeSubstance, or with status 404 (Not Found)
     */
    @GetMapping("/active-substances/{id}")
    @Timed
    public ResponseEntity<ActiveSubstance> getActiveSubstance(@PathVariable String id) {
        log.debug("REST request to get ActiveSubstance : {}", id);
        ActiveSubstance activeSubstance = activeSubstanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(activeSubstance));
    }

    /**
     * DELETE  /active-substances/:id : delete the "id" activeSubstance.
     *
     * @param id the id of the activeSubstance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/active-substances/{id}")
    @Timed
    public ResponseEntity<Void> deleteActiveSubstance(@PathVariable String id) {
        log.debug("REST request to delete ActiveSubstance : {}", id);
        activeSubstanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
