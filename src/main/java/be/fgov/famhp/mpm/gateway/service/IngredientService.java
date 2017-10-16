package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.Ingredient;
import be.fgov.famhp.mpm.gateway.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Ingredient.
 */
@Service
public class IngredientService {

    private final Logger log = LoggerFactory.getLogger(IngredientService.class);

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Save a ingredient.
     *
     * @param ingredient the entity to save
     * @return the persisted entity
     */
    public Ingredient save(Ingredient ingredient) {
        log.debug("Request to save Ingredient : {}", ingredient);
        return ingredientRepository.save(ingredient);
    }

    /**
     *  Get all the ingredients.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Ingredient> findAll(Pageable pageable) {
        log.debug("Request to get all Ingredients");
        return ingredientRepository.findAll(pageable);
    }

    /**
     *  Get one ingredient by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Ingredient findOne(String id) {
        log.debug("Request to get Ingredient : {}", id);
        return ingredientRepository.findOne(id);
    }

    /**
     *  Delete the  ingredient by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Ingredient : {}", id);
        ingredientRepository.delete(id);
    }
}
