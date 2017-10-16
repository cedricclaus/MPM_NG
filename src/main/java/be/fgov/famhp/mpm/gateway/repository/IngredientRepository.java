package be.fgov.famhp.mpm.gateway.repository;

import be.fgov.famhp.mpm.gateway.domain.Ingredient;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Ingredient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientRepository extends MongoRepository<Ingredient, String> {

}
