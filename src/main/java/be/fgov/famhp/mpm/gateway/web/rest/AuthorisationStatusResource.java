package be.fgov.famhp.mpm.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.fgov.famhp.mpm.gateway.domain.AuthorisationStatus;
import be.fgov.famhp.mpm.gateway.service.AuthorisationStatusService;
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
 * REST controller for managing AuthorisationStatus.
 */
@RestController
@RequestMapping("/api")
public class AuthorisationStatusResource {

    private final Logger log = LoggerFactory.getLogger(AuthorisationStatusResource.class);

    private static final String ENTITY_NAME = "authorisationStatus";

    private final AuthorisationStatusService authorisationStatusService;

    public AuthorisationStatusResource(AuthorisationStatusService authorisationStatusService) {
        this.authorisationStatusService = authorisationStatusService;
    }

    /**
     * POST  /authorisation-statuses : Create a new authorisationStatus.
     *
     * @param authorisationStatus the authorisationStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authorisationStatus, or with status 400 (Bad Request) if the authorisationStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/authorisation-statuses")
    @Timed
    public ResponseEntity<AuthorisationStatus> createAuthorisationStatus(@Valid @RequestBody AuthorisationStatus authorisationStatus) throws URISyntaxException {
        log.debug("REST request to save AuthorisationStatus : {}", authorisationStatus);
        if (authorisationStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new authorisationStatus cannot already have an ID")).body(null);
        }
        AuthorisationStatus result = authorisationStatusService.save(authorisationStatus);
        return ResponseEntity.created(new URI("/api/authorisation-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authorisation-statuses : Updates an existing authorisationStatus.
     *
     * @param authorisationStatus the authorisationStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authorisationStatus,
     * or with status 400 (Bad Request) if the authorisationStatus is not valid,
     * or with status 500 (Internal Server Error) if the authorisationStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/authorisation-statuses")
    @Timed
    public ResponseEntity<AuthorisationStatus> updateAuthorisationStatus(@Valid @RequestBody AuthorisationStatus authorisationStatus) throws URISyntaxException {
        log.debug("REST request to update AuthorisationStatus : {}", authorisationStatus);
        if (authorisationStatus.getId() == null) {
            return createAuthorisationStatus(authorisationStatus);
        }
        AuthorisationStatus result = authorisationStatusService.save(authorisationStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authorisationStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authorisation-statuses : get all the authorisationStatuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of authorisationStatuses in body
     */
    @GetMapping("/authorisation-statuses")
    @Timed
    public ResponseEntity<List<AuthorisationStatus>> getAllAuthorisationStatuses(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AuthorisationStatuses");
        Page<AuthorisationStatus> page = authorisationStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authorisation-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /authorisation-statuses/:id : get the "id" authorisationStatus.
     *
     * @param id the id of the authorisationStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authorisationStatus, or with status 404 (Not Found)
     */
    @GetMapping("/authorisation-statuses/{id}")
    @Timed
    public ResponseEntity<AuthorisationStatus> getAuthorisationStatus(@PathVariable String id) {
        log.debug("REST request to get AuthorisationStatus : {}", id);
        AuthorisationStatus authorisationStatus = authorisationStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(authorisationStatus));
    }

    /**
     * DELETE  /authorisation-statuses/:id : delete the "id" authorisationStatus.
     *
     * @param id the id of the authorisationStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/authorisation-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuthorisationStatus(@PathVariable String id) {
        log.debug("REST request to delete AuthorisationStatus : {}", id);
        authorisationStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
