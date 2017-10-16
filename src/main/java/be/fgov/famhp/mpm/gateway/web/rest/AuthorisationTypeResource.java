package be.fgov.famhp.mpm.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.fgov.famhp.mpm.gateway.domain.AuthorisationType;
import be.fgov.famhp.mpm.gateway.service.AuthorisationTypeService;
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
 * REST controller for managing AuthorisationType.
 */
@RestController
@RequestMapping("/api")
public class AuthorisationTypeResource {

    private final Logger log = LoggerFactory.getLogger(AuthorisationTypeResource.class);

    private static final String ENTITY_NAME = "authorisationType";

    private final AuthorisationTypeService authorisationTypeService;

    public AuthorisationTypeResource(AuthorisationTypeService authorisationTypeService) {
        this.authorisationTypeService = authorisationTypeService;
    }

    /**
     * POST  /authorisation-types : Create a new authorisationType.
     *
     * @param authorisationType the authorisationType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authorisationType, or with status 400 (Bad Request) if the authorisationType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/authorisation-types")
    @Timed
    public ResponseEntity<AuthorisationType> createAuthorisationType(@Valid @RequestBody AuthorisationType authorisationType) throws URISyntaxException {
        log.debug("REST request to save AuthorisationType : {}", authorisationType);
        if (authorisationType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new authorisationType cannot already have an ID")).body(null);
        }
        AuthorisationType result = authorisationTypeService.save(authorisationType);
        return ResponseEntity.created(new URI("/api/authorisation-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authorisation-types : Updates an existing authorisationType.
     *
     * @param authorisationType the authorisationType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authorisationType,
     * or with status 400 (Bad Request) if the authorisationType is not valid,
     * or with status 500 (Internal Server Error) if the authorisationType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/authorisation-types")
    @Timed
    public ResponseEntity<AuthorisationType> updateAuthorisationType(@Valid @RequestBody AuthorisationType authorisationType) throws URISyntaxException {
        log.debug("REST request to update AuthorisationType : {}", authorisationType);
        if (authorisationType.getId() == null) {
            return createAuthorisationType(authorisationType);
        }
        AuthorisationType result = authorisationTypeService.save(authorisationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authorisationType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authorisation-types : get all the authorisationTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of authorisationTypes in body
     */
    @GetMapping("/authorisation-types")
    @Timed
    public ResponseEntity<List<AuthorisationType>> getAllAuthorisationTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AuthorisationTypes");
        Page<AuthorisationType> page = authorisationTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authorisation-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /authorisation-types/:id : get the "id" authorisationType.
     *
     * @param id the id of the authorisationType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authorisationType, or with status 404 (Not Found)
     */
    @GetMapping("/authorisation-types/{id}")
    @Timed
    public ResponseEntity<AuthorisationType> getAuthorisationType(@PathVariable String id) {
        log.debug("REST request to get AuthorisationType : {}", id);
        AuthorisationType authorisationType = authorisationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(authorisationType));
    }

    /**
     * DELETE  /authorisation-types/:id : delete the "id" authorisationType.
     *
     * @param id the id of the authorisationType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/authorisation-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuthorisationType(@PathVariable String id) {
        log.debug("REST request to delete AuthorisationType : {}", id);
        authorisationTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
