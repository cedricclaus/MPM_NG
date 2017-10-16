package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.ProductCounterComponent;
import be.fgov.famhp.mpm.gateway.repository.ProductCounterComponentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing ProductCounterComponent.
 */
@Service
public class ProductCounterComponentService {

    private final Logger log = LoggerFactory.getLogger(ProductCounterComponentService.class);

    private final ProductCounterComponentRepository productCounterComponentRepository;

    public ProductCounterComponentService(ProductCounterComponentRepository productCounterComponentRepository) {
        this.productCounterComponentRepository = productCounterComponentRepository;
    }

    /**
     * Save a productCounterComponent.
     *
     * @param productCounterComponent the entity to save
     * @return the persisted entity
     */
    public ProductCounterComponent save(ProductCounterComponent productCounterComponent) {
        log.debug("Request to save ProductCounterComponent : {}", productCounterComponent);
        return productCounterComponentRepository.save(productCounterComponent);
    }

    /**
     *  Get all the productCounterComponents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<ProductCounterComponent> findAll(Pageable pageable) {
        log.debug("Request to get all ProductCounterComponents");
        return productCounterComponentRepository.findAll(pageable);
    }

    /**
     *  Get one productCounterComponent by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public ProductCounterComponent findOne(String id) {
        log.debug("Request to get ProductCounterComponent : {}", id);
        return productCounterComponentRepository.findOne(id);
    }

    /**
     *  Delete the  productCounterComponent by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ProductCounterComponent : {}", id);
        productCounterComponentRepository.delete(id);
    }
}
