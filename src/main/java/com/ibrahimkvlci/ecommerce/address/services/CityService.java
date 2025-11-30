package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.CityRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CityResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.City;

import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;

import java.util.List;

/**
 * Service interface for City operations
 */
public interface CityService {

    DataResult<CityResponseDTO> createCity(CityRequestDTO cityRequestDTO);

    DataResult<CityResponseDTO> getCityById(Long id);

    DataResult<List<CityResponseDTO>> getAllCities();

    DataResult<List<CityResponseDTO>> getCitiesByCountryId(Long countryId);

    DataResult<List<CityResponseDTO>> searchCitiesByName(String name);

    DataResult<List<CityResponseDTO>> searchCitiesByCountryAndName(Long countryId, String name);

    DataResult<CityResponseDTO> updateCity(Long id, CityRequestDTO cityRequestDTO);

    Result deleteCity(Long id);

    boolean existsByNameAndCountryId(String name, Long countryId);

    CityResponseDTO mapToDTO(City city);

    City mapToEntity(CityRequestDTO cityRequestDTO);
}
