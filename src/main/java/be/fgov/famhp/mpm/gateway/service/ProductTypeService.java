package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.ProductType;
import be.fgov.famhp.mpm.gateway.repository.ProductTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing ProductType.
 */
@Service
public class ProductTypeService {

    private final Logger log = LoggerFactory.getLogger(ProductTypeService.class);

    private final ProductTypeRepository productTypeRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    /**
     * Save a productType.
     *
     * @param productType the entity to save
     * @return the persisted entity
     */
    public ProductType save(ProductType productType) {
        log.debug("Request to save ProductType : {}", productType);
        return productTypeRepository.save(productType);
    }

    /**
     *  Get all the productTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<ProductType> findAll(Pageable pageable) {
        log.debug("Request to get all ProductTypes");
        return productTypeRepository.findAll(pageable);
    }

    /**
     *  Get one productType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public ProductType findOne(String id) {
        log.debug("Request to get ProductType : {}", id);
        return productTypeRepository.findOne(id);
    }

    /**
     *  Delete the  productType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ProductType : {}", id);
        productTypeRepository.delete(id);
    }
}
