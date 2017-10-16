package be.fgov.famhp.mpm.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.fgov.famhp.mpm.gateway.domain.ProductGroup;
import be.fgov.famhp.mpm.gateway.service.ProductGroupService;
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
 * REST controller for managing ProductGroup.
 */
@RestController
@RequestMapping("/api")
public class ProductGroupResource {

    private final Logger log = LoggerFactory.getLogger(ProductGroupResource.class);

    private static final String ENTITY_NAME = "productGroup";

    private final ProductGroupService productGroupService;

    public ProductGroupResource(ProductGroupService productGroupService) {
        this.productGroupService = productGroupService;
    }

    /**
     * POST  /product-groups : Create a new productGroup.
     *
     * @param productGroup the productGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productGroup, or with status 400 (Bad Request) if the productGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-groups")
    @Timed
    public ResponseEntity<ProductGroup> createProductGroup(@Valid @RequestBody ProductGroup productGroup) throws URISyntaxException {
        log.debug("REST request to save ProductGroup : {}", productGroup);
        if (productGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new productGroup cannot already have an ID")).body(null);
        }
        ProductGroup result = productGroupService.save(productGroup);
        return ResponseEntity.created(new URI("/api/product-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-groups : Updates an existing productGroup.
     *
     * @param productGroup the productGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productGroup,
     * or with status 400 (Bad Request) if the productGroup is not valid,
     * or with status 500 (Internal Server Error) if the productGroup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-groups")
    @Timed
    public ResponseEntity<ProductGroup> updateProductGroup(@Valid @RequestBody ProductGroup productGroup) throws URISyntaxException {
        log.debug("REST request to update ProductGroup : {}", productGroup);
        if (productGroup.getId() == null) {
            return createProductGroup(productGroup);
        }
        ProductGroup result = productGroupService.save(productGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-groups : get all the productGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productGroups in body
     */
    @GetMapping("/product-groups")
    @Timed
    public ResponseEntity<List<ProductGroup>> getAllProductGroups(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ProductGroups");
        Page<ProductGroup> page = productGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /product-groups/:id : get the "id" productGroup.
     *
     * @param id the id of the productGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productGroup, or with status 404 (Not Found)
     */
    @GetMapping("/product-groups/{id}")
    @Timed
    public ResponseEntity<ProductGroup> getProductGroup(@PathVariable String id) {
        log.debug("REST request to get ProductGroup : {}", id);
        ProductGroup productGroup = productGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productGroup));
    }

    /**
     * DELETE  /product-groups/:id : delete the "id" productGroup.
     *
     * @param id the id of the productGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductGroup(@PathVariable String id) {
        log.debug("REST request to delete ProductGroup : {}", id);
        productGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
