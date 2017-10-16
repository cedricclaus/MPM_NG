package be.fgov.famhp.mpm.gateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.fgov.famhp.mpm.gateway.domain.ProductCounterComponent;
import be.fgov.famhp.mpm.gateway.service.ProductCounterComponentService;
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
 * REST controller for managing ProductCounterComponent.
 */
@RestController
@RequestMapping("/api")
public class ProductCounterComponentResource {

    private final Logger log = LoggerFactory.getLogger(ProductCounterComponentResource.class);

    private static final String ENTITY_NAME = "productCounterComponent";

    private final ProductCounterComponentService productCounterComponentService;

    public ProductCounterComponentResource(ProductCounterComponentService productCounterComponentService) {
        this.productCounterComponentService = productCounterComponentService;
    }

    /**
     * POST  /product-counter-components : Create a new productCounterComponent.
     *
     * @param productCounterComponent the productCounterComponent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productCounterComponent, or with status 400 (Bad Request) if the productCounterComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-counter-components")
    @Timed
    public ResponseEntity<ProductCounterComponent> createProductCounterComponent(@Valid @RequestBody ProductCounterComponent productCounterComponent) throws URISyntaxException {
        log.debug("REST request to save ProductCounterComponent : {}", productCounterComponent);
        if (productCounterComponent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new productCounterComponent cannot already have an ID")).body(null);
        }
        ProductCounterComponent result = productCounterComponentService.save(productCounterComponent);
        return ResponseEntity.created(new URI("/api/product-counter-components/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-counter-components : Updates an existing productCounterComponent.
     *
     * @param productCounterComponent the productCounterComponent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productCounterComponent,
     * or with status 400 (Bad Request) if the productCounterComponent is not valid,
     * or with status 500 (Internal Server Error) if the productCounterComponent couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-counter-components")
    @Timed
    public ResponseEntity<ProductCounterComponent> updateProductCounterComponent(@Valid @RequestBody ProductCounterComponent productCounterComponent) throws URISyntaxException {
        log.debug("REST request to update ProductCounterComponent : {}", productCounterComponent);
        if (productCounterComponent.getId() == null) {
            return createProductCounterComponent(productCounterComponent);
        }
        ProductCounterComponent result = productCounterComponentService.save(productCounterComponent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productCounterComponent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-counter-components : get all the productCounterComponents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productCounterComponents in body
     */
    @GetMapping("/product-counter-components")
    @Timed
    public ResponseEntity<List<ProductCounterComponent>> getAllProductCounterComponents(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ProductCounterComponents");
        Page<ProductCounterComponent> page = productCounterComponentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-counter-components");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /product-counter-components/:id : get the "id" productCounterComponent.
     *
     * @param id the id of the productCounterComponent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productCounterComponent, or with status 404 (Not Found)
     */
    @GetMapping("/product-counter-components/{id}")
    @Timed
    public ResponseEntity<ProductCounterComponent> getProductCounterComponent(@PathVariable String id) {
        log.debug("REST request to get ProductCounterComponent : {}", id);
        ProductCounterComponent productCounterComponent = productCounterComponentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productCounterComponent));
    }

    /**
     * DELETE  /product-counter-components/:id : delete the "id" productCounterComponent.
     *
     * @param id the id of the productCounterComponent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-counter-components/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductCounterComponent(@PathVariable String id) {
        log.debug("REST request to delete ProductCounterComponent : {}", id);
        productCounterComponentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
