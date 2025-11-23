package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.CountryRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CountryResponseDTO;
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
    CountryResponseDTO createCountry(CountryRequestDTO countryRequestDTO);

    /**
     * Get country by ID
     */
    Optional<CountryResponseDTO> getCountryById(Long id);

    /**
     * Get country by name
     */
    Optional<CountryResponseDTO> getCountryByName(String name);

    /**
     * Get country by code
     */
    Optional<CountryResponseDTO> getCountryByCode(String code);

    /**
     * Get all countries
     */
    List<CountryResponseDTO> getAllCountries();

    /**
     * Search countries by name
     */
    List<CountryResponseDTO> searchCountriesByName(String name);

    /**
     * Update country
     */
    CountryResponseDTO updateCountry(Long id, CountryRequestDTO countryRequestDTO);

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
    CountryResponseDTO mapToDTO(Country country);

    /**
     * Map DTO to Country entity
     */
    Country mapToEntity(CountryRequestDTO countryRequestDTO);
}
