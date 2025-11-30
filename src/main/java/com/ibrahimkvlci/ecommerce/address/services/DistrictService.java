package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.DistrictRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.DistrictResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.District;

import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;

import java.util.List;

/**
 * Service interface for District operations
 */
public interface DistrictService {

    /**
     * Create a new district
     */
    DataResult<DistrictResponseDTO> createDistrict(DistrictRequestDTO districtRequestDTO);

    /**
     * Get district by ID
     */
    DataResult<DistrictResponseDTO> getDistrictById(Long id);

    /**
     * Get all districts
     */
    DataResult<List<DistrictResponseDTO>> getAllDistricts();

    /**
     * Get districts by city ID
     */
    DataResult<List<DistrictResponseDTO>> getDistrictsByCityId(Long cityId);

    /**
     * Search districts by name
     */
    DataResult<List<DistrictResponseDTO>> searchDistrictsByName(String name);

    /**
     * Update district
     */
    DataResult<DistrictResponseDTO> updateDistrict(Long id, DistrictRequestDTO districtRequestDTO);

    /**
     * Delete district
     */
    Result deleteDistrict(Long id);

    /**
     * Map District entity to DTO
     */
    DistrictResponseDTO mapToDTO(District district);

    /**
     * Map DTO to District entity
     */
    District mapToEntity(DistrictRequestDTO districtRequestDTO);
}
