package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.ProductCounter;
import be.fgov.famhp.mpm.gateway.repository.ProductCounterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing ProductCounter.
 */
@Service
public class ProductCounterService {

    private final Logger log = LoggerFactory.getLogger(ProductCounterService.class);

    private final ProductCounterRepository productCounterRepository;

    public ProductCounterService(ProductCounterRepository productCounterRepository) {
        this.productCounterRepository = productCounterRepository;
    }

    /**
     * Save a productCounter.
     *
     * @param productCounter the entity to save
     * @return the persisted entity
     */
    public ProductCounter save(ProductCounter productCounter) {
        log.debug("Request to save ProductCounter : {}", productCounter);
        return productCounterRepository.save(productCounter);
    }

    /**
     *  Get all the productCounters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<ProductCounter> findAll(Pageable pageable) {
        log.debug("Request to get all ProductCounters");
        return productCounterRepository.findAll(pageable);
    }

    /**
     *  Get one productCounter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public ProductCounter findOne(String id) {
        log.debug("Request to get ProductCounter : {}", id);
        return productCounterRepository.findOne(id);
    }

    /**
     *  Delete the  productCounter by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ProductCounter : {}", id);
        productCounterRepository.delete(id);
    }
}
