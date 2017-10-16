package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.ProductGroup;
import be.fgov.famhp.mpm.gateway.repository.ProductGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing ProductGroup.
 */
@Service
public class ProductGroupService {

    private final Logger log = LoggerFactory.getLogger(ProductGroupService.class);

    private final ProductGroupRepository productGroupRepository;

    public ProductGroupService(ProductGroupRepository productGroupRepository) {
        this.productGroupRepository = productGroupRepository;
    }

    /**
     * Save a productGroup.
     *
     * @param productGroup the entity to save
     * @return the persisted entity
     */
    public ProductGroup save(ProductGroup productGroup) {
        log.debug("Request to save ProductGroup : {}", productGroup);
        return productGroupRepository.save(productGroup);
    }

    /**
     *  Get all the productGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<ProductGroup> findAll(Pageable pageable) {
        log.debug("Request to get all ProductGroups");
        return productGroupRepository.findAll(pageable);
    }

    /**
     *  Get one productGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public ProductGroup findOne(String id) {
        log.debug("Request to get ProductGroup : {}", id);
        return productGroupRepository.findOne(id);
    }

    /**
     *  Delete the  productGroup by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ProductGroup : {}", id);
        productGroupRepository.delete(id);
    }
}
