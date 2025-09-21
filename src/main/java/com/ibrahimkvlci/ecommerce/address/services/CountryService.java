package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.CountryDTO;
import com.ibrahimkvlci.ecommerce.address.models.Country;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Country operations
 */
public interface CountryService {
    
    /**
     * Create a new country
     */
    Country createCountry(CountryDTO countryDTO);
    
    /**
     * Get country by ID
     */
    Optional<Country> getCountryById(Long id);
    
    /**
     * Get country by name
     */
    Optional<Country> getCountryByName(String name);
    
    /**
     * Get country by code
     */
    Optional<Country> getCountryByCode(String code);
    
    /**
     * Get all countries
     */
    List<Country> getAllCountries();
    
    /**
     * Search countries by name
     */
    List<Country> searchCountriesByName(String name);
    
    /**
     * Update country
     */
    Country updateCountry(Long id, CountryDTO countryDTO);
    
    /**
     * Delete country
     */
    void deleteCountry(Long id);
    
    /**
     * Check if country exists by name
     */
    boolean existsByName(String name);
    
    /**
     * Check if country exists by code
     */
    boolean existsByCode(String code);
    
    /**
     * Map Country entity to DTO
     */
    CountryDTO mapToDTO(Country country);
    
    /**
     * Map DTO to Country entity
     */
    Country mapToEntity(CountryDTO countryDTO);
}
