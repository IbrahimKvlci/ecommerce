package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.CountryRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CountryResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.Country;

import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;

import java.util.List;

/**
 * Service interface for Country operations
 */
public interface CountryService {

    /**
     * Create a new country
     */
    DataResult<CountryResponseDTO> createCountry(CountryRequestDTO countryRequestDTO);

    /**
     * Get country by ID
     */
    DataResult<CountryResponseDTO> getCountryById(Long id);

    /**
     * Get country by name
     */
    DataResult<CountryResponseDTO> getCountryByName(String name);

    /**
     * Get country by code
     */
    DataResult<CountryResponseDTO> getCountryByCode(String code);

    /**
     * Get all countries
     */
    DataResult<List<CountryResponseDTO>> getAllCountries();

    /**
     * Search countries by name
     */
    DataResult<List<CountryResponseDTO>> searchCountriesByName(String name);

    /**
     * Update country
     */
    DataResult<CountryResponseDTO> updateCountry(Long id, CountryRequestDTO countryRequestDTO);

    /**
     * Delete country
     */
    Result deleteCountry(Long id);

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
