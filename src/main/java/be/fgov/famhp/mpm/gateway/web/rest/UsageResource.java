package be.fgov.famhp.mpm.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.fgov.famhp.mpm.gateway.domain.Usage;
import be.fgov.famhp.mpm.gateway.service.UsageService;
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
 * REST controller for managing Usage.
 */
@RestController
@RequestMapping("/api")
public class UsageResource {

    private final Logger log = LoggerFactory.getLogger(UsageResource.class);

    private static final String ENTITY_NAME = "usage";

    private final UsageService usageService;

    public UsageResource(UsageService usageService) {
        this.usageService = usageService;
    }

    /**
     * POST  /usages : Create a new usage.
     *
     * @param usage the usage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usage, or with status 400 (Bad Request) if the usage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/usages")
    @Timed
    public ResponseEntity<Usage> createUsage(@Valid @RequestBody Usage usage) throws URISyntaxException {
        log.debug("REST request to save Usage : {}", usage);
        if (usage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new usage cannot already have an ID")).body(null);
        }
        Usage result = usageService.save(usage);
        return ResponseEntity.created(new URI("/api/usages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /usages : Updates an existing usage.
     *
     * @param usage the usage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usage,
     * or with status 400 (Bad Request) if the usage is not valid,
     * or with status 500 (Internal Server Error) if the usage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/usages")
    @Timed
    public ResponseEntity<Usage> updateUsage(@Valid @RequestBody Usage usage) throws URISyntaxException {
        log.debug("REST request to update Usage : {}", usage);
        if (usage.getId() == null) {
            return createUsage(usage);
        }
        Usage result = usageService.save(usage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, usage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /usages : get all the usages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of usages in body
     */
    @GetMapping("/usages")
    @Timed
    public ResponseEntity<List<Usage>> getAllUsages(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Usages");
        Page<Usage> page = usageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/usages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /usages/:id : get the "id" usage.
     *
     * @param id the id of the usage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usage, or with status 404 (Not Found)
     */
    @GetMapping("/usages/{id}")
    @Timed
    public ResponseEntity<Usage> getUsage(@PathVariable String id) {
        log.debug("REST request to get Usage : {}", id);
        Usage usage = usageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(usage));
    }

    /**
     * DELETE  /usages/:id : delete the "id" usage.
     *
     * @param id the id of the usage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/usages/{id}")
    @Timed
    public ResponseEntity<Void> deleteUsage(@PathVariable String id) {
        log.debug("REST request to delete Usage : {}", id);
        usageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
