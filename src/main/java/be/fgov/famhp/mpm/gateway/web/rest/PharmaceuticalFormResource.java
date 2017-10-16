package be.fgov.famhp.mpm.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.fgov.famhp.mpm.gateway.domain.PharmaceuticalForm;
import be.fgov.famhp.mpm.gateway.service.PharmaceuticalFormService;
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
 * REST controller for managing PharmaceuticalForm.
 */
@RestController
@RequestMapping("/api")
public class PharmaceuticalFormResource {

    private final Logger log = LoggerFactory.getLogger(PharmaceuticalFormResource.class);

    private static final String ENTITY_NAME = "pharmaceuticalForm";

    private final PharmaceuticalFormService pharmaceuticalFormService;

    public PharmaceuticalFormResource(PharmaceuticalFormService pharmaceuticalFormService) {
        this.pharmaceuticalFormService = pharmaceuticalFormService;
    }

    /**
     * POST  /pharmaceutical-forms : Create a new pharmaceuticalForm.
     *
     * @param pharmaceuticalForm the pharmaceuticalForm to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pharmaceuticalForm, or with status 400 (Bad Request) if the pharmaceuticalForm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pharmaceutical-forms")
    @Timed
    public ResponseEntity<PharmaceuticalForm> createPharmaceuticalForm(@Valid @RequestBody PharmaceuticalForm pharmaceuticalForm) throws URISyntaxException {
        log.debug("REST request to save PharmaceuticalForm : {}", pharmaceuticalForm);
        if (pharmaceuticalForm.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pharmaceuticalForm cannot already have an ID")).body(null);
        }
        PharmaceuticalForm result = pharmaceuticalFormService.save(pharmaceuticalForm);
        return ResponseEntity.created(new URI("/api/pharmaceutical-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pharmaceutical-forms : Updates an existing pharmaceuticalForm.
     *
     * @param pharmaceuticalForm the pharmaceuticalForm to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pharmaceuticalForm,
     * or with status 400 (Bad Request) if the pharmaceuticalForm is not valid,
     * or with status 500 (Internal Server Error) if the pharmaceuticalForm couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pharmaceutical-forms")
    @Timed
    public ResponseEntity<PharmaceuticalForm> updatePharmaceuticalForm(@Valid @RequestBody PharmaceuticalForm pharmaceuticalForm) throws URISyntaxException {
        log.debug("REST request to update PharmaceuticalForm : {}", pharmaceuticalForm);
        if (pharmaceuticalForm.getId() == null) {
            return createPharmaceuticalForm(pharmaceuticalForm);
        }
        PharmaceuticalForm result = pharmaceuticalFormService.save(pharmaceuticalForm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pharmaceuticalForm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pharmaceutical-forms : get all the pharmaceuticalForms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pharmaceuticalForms in body
     */
    @GetMapping("/pharmaceutical-forms")
    @Timed
    public ResponseEntity<List<PharmaceuticalForm>> getAllPharmaceuticalForms(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PharmaceuticalForms");
        Page<PharmaceuticalForm> page = pharmaceuticalFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pharmaceutical-forms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pharmaceutical-forms/:id : get the "id" pharmaceuticalForm.
     *
     * @param id the id of the pharmaceuticalForm to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pharmaceuticalForm, or with status 404 (Not Found)
     */
    @GetMapping("/pharmaceutical-forms/{id}")
    @Timed
    public ResponseEntity<PharmaceuticalForm> getPharmaceuticalForm(@PathVariable String id) {
        log.debug("REST request to get PharmaceuticalForm : {}", id);
        PharmaceuticalForm pharmaceuticalForm = pharmaceuticalFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pharmaceuticalForm));
    }

    /**
     * DELETE  /pharmaceutical-forms/:id : delete the "id" pharmaceuticalForm.
     *
     * @param id the id of the pharmaceuticalForm to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pharmaceutical-forms/{id}")
    @Timed
    public ResponseEntity<Void> deletePharmaceuticalForm(@PathVariable String id) {
        log.debug("REST request to delete PharmaceuticalForm : {}", id);
        pharmaceuticalFormService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
