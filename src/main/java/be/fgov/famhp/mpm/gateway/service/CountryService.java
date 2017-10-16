package be.fgov.famhp.mpm.gateway.service;

import be.fgov.famhp.mpm.gateway.domain.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Country.
 */
public interface CountryService {

    /**
     * Save a country.
     *
     * @param country the entity to save
     * @return the persisted entity
     */
    Country save(Country country);

    /**
     *  Get all the countries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Country> findAll(Pageable pageable);

    /**
     *  Get the "id" country.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Country findOne(String id);

    /**
     *  Delete the "id" country.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
