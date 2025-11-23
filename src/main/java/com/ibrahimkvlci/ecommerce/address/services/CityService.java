package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.CityRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CityResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.City;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for City operations
 */
public interface CityService {

    CityResponseDTO createCity(CityRequestDTO cityRequestDTO);

    Optional<CityResponseDTO> getCityById(Long id);

    List<CityResponseDTO> getAllCities();

    List<CityResponseDTO> getCitiesByCountryId(Long countryId);

    List<CityResponseDTO> searchCitiesByName(String name);

    List<CityResponseDTO> searchCitiesByCountryAndName(Long countryId, String name);

    CityResponseDTO updateCity(Long id, CityRequestDTO cityRequestDTO);

    void deleteCity(Long id);

    boolean existsByNameAndCountryId(String name, Long countryId);

    CityResponseDTO mapToDTO(City city);

    City mapToEntity(CityRequestDTO cityRequestDTO);
}
