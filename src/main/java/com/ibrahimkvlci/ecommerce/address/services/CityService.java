package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.CityDTO;
import com.ibrahimkvlci.ecommerce.address.models.City;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for City operations
 */
public interface CityService {
    
    City createCity(CityDTO cityDTO);
    Optional<City> getCityById(Long id);
    List<City> getAllCities();
    List<City> getCitiesByCountryId(Long countryId);
    List<City> searchCitiesByName(String name);
    List<City> searchCitiesByCountryAndName(Long countryId, String name);
    City updateCity(Long id, CityDTO cityDTO);
    void deleteCity(Long id);
    boolean existsByNameAndCountryId(String name, Long countryId);
    CityDTO mapToDTO(City city);
    City mapToEntity(CityDTO cityDTO);
}
