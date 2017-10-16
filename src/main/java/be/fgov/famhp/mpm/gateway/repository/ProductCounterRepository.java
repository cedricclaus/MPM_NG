package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.ProductCounter;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProductCounter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductCounterRepository extends MongoRepository<ProductCounter, String> {

}
