package be.fgov.famhp.mpm.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.fgov.famhp.mpm.gateway.domain.ProcedureType;
import be.fgov.famhp.mpm.gateway.service.ProcedureTypeService;
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
 * REST controller for managing ProcedureType.
 */
@RestController
@RequestMapping("/api")
public class ProcedureTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProcedureTypeResource.class);

    private static final String ENTITY_NAME = "procedureType";

    private final ProcedureTypeService procedureTypeService;

    public ProcedureTypeResource(ProcedureTypeService procedureTypeService) {
        this.procedureTypeService = procedureTypeService;
    }

    /**
     * POST  /procedure-types : Create a new procedureType.
     *
     * @param procedureType the procedureType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procedureType, or with status 400 (Bad Request) if the procedureType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/procedure-types")
    @Timed
    public ResponseEntity<ProcedureType> createProcedureType(@Valid @RequestBody ProcedureType procedureType) throws URISyntaxException {
        log.debug("REST request to save ProcedureType : {}", procedureType);
        if (procedureType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new procedureType cannot already have an ID")).body(null);
        }
        ProcedureType result = procedureTypeService.save(procedureType);
        return ResponseEntity.created(new URI("/api/procedure-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /procedure-types : Updates an existing procedureType.
     *
     * @param procedureType the procedureType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procedureType,
     * or with status 400 (Bad Request) if the procedureType is not valid,
     * or with status 500 (Internal Server Error) if the procedureType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/procedure-types")
    @Timed
    public ResponseEntity<ProcedureType> updateProcedureType(@Valid @RequestBody ProcedureType procedureType) throws URISyntaxException {
        log.debug("REST request to update ProcedureType : {}", procedureType);
        if (procedureType.getId() == null) {
            return createProcedureType(procedureType);
        }
        ProcedureType result = procedureTypeService.save(procedureType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, procedureType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procedure-types : get all the procedureTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of procedureTypes in body
     */
    @GetMapping("/procedure-types")
    @Timed
    public ResponseEntity<List<ProcedureType>> getAllProcedureTypes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ProcedureTypes");
        Page<ProcedureType> page = procedureTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/procedure-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /procedure-types/:id : get the "id" procedureType.
     *
     * @param id the id of the procedureType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procedureType, or with status 404 (Not Found)
     */
    @GetMapping("/procedure-types/{id}")
    @Timed
    public ResponseEntity<ProcedureType> getProcedureType(@PathVariable String id) {
        log.debug("REST request to get ProcedureType : {}", id);
        ProcedureType procedureType = procedureTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(procedureType));
    }

    /**
     * DELETE  /procedure-types/:id : delete the "id" procedureType.
     *
     * @param id the id of the procedureType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/procedure-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcedureType(@PathVariable String id) {
        log.debug("REST request to delete ProcedureType : {}", id);
        procedureTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
