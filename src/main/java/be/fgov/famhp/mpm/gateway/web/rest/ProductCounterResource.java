package be.fgov.famhp.mpm.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.fgov.famhp.mpm.gateway.domain.ProductCounter;
import be.fgov.famhp.mpm.gateway.service.ProductCounterService;
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
 * REST controller for managing ProductCounter.
 */
@RestController
@RequestMapping("/api")
public class ProductCounterResource {

    private final Logger log = LoggerFactory.getLogger(ProductCounterResource.class);

    private static final String ENTITY_NAME = "productCounter";

    private final ProductCounterService productCounterService;

    public ProductCounterResource(ProductCounterService productCounterService) {
        this.productCounterService = productCounterService;
    }

    /**
     * POST  /product-counters : Create a new productCounter.
     *
     * @param productCounter the productCounter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productCounter, or with status 400 (Bad Request) if the productCounter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-counters")
    @Timed
    public ResponseEntity<ProductCounter> createProductCounter(@Valid @RequestBody ProductCounter productCounter) throws URISyntaxException {
        log.debug("REST request to save ProductCounter : {}", productCounter);
        if (productCounter.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new productCounter cannot already have an ID")).body(null);
        }
        ProductCounter result = productCounterService.save(productCounter);
        return ResponseEntity.created(new URI("/api/product-counters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-counters : Updates an existing productCounter.
     *
     * @param productCounter the productCounter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productCounter,
     * or with status 400 (Bad Request) if the productCounter is not valid,
     * or with status 500 (Internal Server Error) if the productCounter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-counters")
    @Timed
    public ResponseEntity<ProductCounter> updateProductCounter(@Valid @RequestBody ProductCounter productCounter) throws URISyntaxException {
        log.debug("REST request to update ProductCounter : {}", productCounter);
        if (productCounter.getId() == null) {
            return createProductCounter(productCounter);
        }
        ProductCounter result = productCounterService.save(productCounter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productCounter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-counters : get all the productCounters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productCounters in body
     */
    @GetMapping("/product-counters")
    @Timed
    public ResponseEntity<List<ProductCounter>> getAllProductCounters(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ProductCounters");
        Page<ProductCounter> page = productCounterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-counters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /product-counters/:id : get the "id" productCounter.
     *
     * @param id the id of the productCounter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productCounter, or with status 404 (Not Found)
     */
    @GetMapping("/product-counters/{id}")
    @Timed
    public ResponseEntity<ProductCounter> getProductCounter(@PathVariable String id) {
        log.debug("REST request to get ProductCounter : {}", id);
        ProductCounter productCounter = productCounterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productCounter));
    }

    /**
     * DELETE  /product-counters/:id : delete the "id" productCounter.
     *
     * @param id the id of the productCounter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-counters/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductCounter(@PathVariable String id) {
        log.debug("REST request to delete ProductCounter : {}", id);
        productCounterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
