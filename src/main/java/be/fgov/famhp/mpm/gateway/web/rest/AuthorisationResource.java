package be.fgov.famhp.mpm.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.fgov.famhp.mpm.gateway.domain.Authorisation;
import be.fgov.famhp.mpm.gateway.service.AuthorisationService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Authorisation.
 */
@RestController
@RequestMapping("/api")
public class AuthorisationResource {

    private final Logger log = LoggerFactory.getLogger(AuthorisationResource.class);

    private static final String ENTITY_NAME = "authorisation";

    private final AuthorisationService authorisationService;

    public AuthorisationResource(AuthorisationService authorisationService) {
        this.authorisationService = authorisationService;
    }

    /**
     * POST  /authorisations : Create a new authorisation.
     *
     * @param authorisation the authorisation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authorisation, or with status 400 (Bad Request) if the authorisation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/authorisations")
    @Timed
    public ResponseEntity<Authorisation> createAuthorisation(@RequestBody Authorisation authorisation) throws URISyntaxException {
        log.debug("REST request to save Authorisation : {}", authorisation);
        if (authorisation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new authorisation cannot already have an ID")).body(null);
        }
        Authorisation result = authorisationService.save(authorisation);
        return ResponseEntity.created(new URI("/api/authorisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authorisations : Updates an existing authorisation.
     *
     * @param authorisation the authorisation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authorisation,
     * or with status 400 (Bad Request) if the authorisation is not valid,
     * or with status 500 (Internal Server Error) if the authorisation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/authorisations")
    @Timed
    public ResponseEntity<Authorisation> updateAuthorisation(@RequestBody Authorisation authorisation) throws URISyntaxException {
        log.debug("REST request to update Authorisation : {}", authorisation);
        if (authorisation.getId() == null) {
            return createAuthorisation(authorisation);
        }
        Authorisation result = authorisationService.save(authorisation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authorisation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authorisations : get all the authorisations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of authorisations in body
     */
    @GetMapping("/authorisations")
    @Timed
    public ResponseEntity<List<Authorisation>> getAllAuthorisations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Authorisations");
        Page<Authorisation> page = authorisationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authorisations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /authorisations/:id : get the "id" authorisation.
     *
     * @param id the id of the authorisation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authorisation, or with status 404 (Not Found)
     */
    @GetMapping("/authorisations/{id}")
    @Timed
    public ResponseEntity<Authorisation> getAuthorisation(@PathVariable String id) {
        log.debug("REST request to get Authorisation : {}", id);
        Authorisation authorisation = authorisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(authorisation));
    }

    /**
     * DELETE  /authorisations/:id : delete the "id" authorisation.
     *
     * @param id the id of the authorisation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/authorisations/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuthorisation(@PathVariable String id) {
        log.debug("REST request to delete Authorisation : {}", id);
        authorisationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
