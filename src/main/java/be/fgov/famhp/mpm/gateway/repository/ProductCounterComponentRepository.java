package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.ProductCounterComponent;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProductCounterComponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductCounterComponentRepository extends MongoRepository<ProductCounterComponent, String> {

}
