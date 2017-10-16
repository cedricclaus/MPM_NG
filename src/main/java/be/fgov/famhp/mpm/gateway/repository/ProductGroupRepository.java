package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.ProductGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProductGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductGroupRepository extends MongoRepository<ProductGroup, String> {

}
