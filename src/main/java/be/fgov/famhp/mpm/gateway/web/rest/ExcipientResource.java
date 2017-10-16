package be.fgov.famhp.mpm.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.fgov.famhp.mpm.gateway.domain.Excipient;
import be.fgov.famhp.mpm.gateway.service.ExcipientService;
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
 * REST controller for managing Excipient.
 */
@RestController
@RequestMapping("/api")
public class ExcipientResource {

    private final Logger log = LoggerFactory.getLogger(ExcipientResource.class);

    private static final String ENTITY_NAME = "excipient";

    private final ExcipientService excipientService;

    public ExcipientResource(ExcipientService excipientService) {
        this.excipientService = excipientService;
    }

    /**
     * POST  /excipients : Create a new excipient.
     *
     * @param excipient the excipient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new excipient, or with status 400 (Bad Request) if the excipient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/excipients")
    @Timed
    public ResponseEntity<Excipient> createExcipient(@Valid @RequestBody Excipient excipient) throws URISyntaxException {
        log.debug("REST request to save Excipient : {}", excipient);
        if (excipient.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new excipient cannot already have an ID")).body(null);
        }
        Excipient result = excipientService.save(excipient);
        return ResponseEntity.created(new URI("/api/excipients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /excipients : Updates an existing excipient.
     *
     * @param excipient the excipient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated excipient,
     * or with status 400 (Bad Request) if the excipient is not valid,
     * or with status 500 (Internal Server Error) if the excipient couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/excipients")
    @Timed
    public ResponseEntity<Excipient> updateExcipient(@Valid @RequestBody Excipient excipient) throws URISyntaxException {
        log.debug("REST request to update Excipient : {}", excipient);
        if (excipient.getId() == null) {
            return createExcipient(excipient);
        }
        Excipient result = excipientService.save(excipient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, excipient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /excipients : get all the excipients.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of excipients in body
     */
    @GetMapping("/excipients")
    @Timed
    public ResponseEntity<List<Excipient>> getAllExcipients(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Excipients");
        Page<Excipient> page = excipientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/excipients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /excipients/:id : get the "id" excipient.
     *
     * @param id the id of the excipient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the excipient, or with status 404 (Not Found)
     */
    @GetMapping("/excipients/{id}")
    @Timed
    public ResponseEntity<Excipient> getExcipient(@PathVariable String id) {
        log.debug("REST request to get Excipient : {}", id);
        Excipient excipient = excipientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(excipient));
    }

    /**
     * DELETE  /excipients/:id : delete the "id" excipient.
     *
     * @param id the id of the excipient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/excipients/{id}")
    @Timed
    public ResponseEntity<Void> deleteExcipient(@PathVariable String id) {
        log.debug("REST request to delete Excipient : {}", id);
        excipientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
