package be.fgov.famhp.mpm.gateway.service.impl;

import be.fgov.famhp.mpm.gateway.service.CountryService;
import be.fgov.famhp.mpm.gateway.domain.Country;
import be.fgov.famhp.mpm.gateway.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Country.
 */
@Service
public class CountryServiceImpl implements CountryService{

    private final Logger log = LoggerFactory.getLogger(CountryServiceImpl.class);

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * Save a country.
     *
     * @param country the entity to save
     * @return the persisted entity
     */
    @Override
    public Country save(Country country) {
        log.debug("Request to save Country : {}", country);
        return countryRepository.save(country);
    }

    /**
     *  Get all the countries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Country> findAll(Pageable pageable) {
        log.debug("Request to get all Countries");
        return countryRepository.findAll(pageable);
    }

    /**
     *  Get one country by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Country findOne(String id) {
        log.debug("Request to get Country : {}", id);
        return countryRepository.findOne(id);
    }

    /**
     *  Delete the  country by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Country : {}", id);
        countryRepository.delete(id);
    }
}
