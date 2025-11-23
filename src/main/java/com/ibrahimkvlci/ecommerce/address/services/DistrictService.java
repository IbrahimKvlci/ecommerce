package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.DistrictRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.DistrictResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.District;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for District operations
 */
public interface DistrictService {

    /**
     * Create a new district
     */
    DistrictResponseDTO createDistrict(DistrictRequestDTO districtRequestDTO);

    /**
     * Get district by ID
     */
    Optional<DistrictResponseDTO> getDistrictById(Long id);

    /**
     * Get all districts
     */
    List<DistrictResponseDTO> getAllDistricts();

    /**
     * Get districts by city ID
     */
    List<DistrictResponseDTO> getDistrictsByCityId(Long cityId);

    /**
     * Search districts by name
     */
    List<DistrictResponseDTO> searchDistrictsByName(String name);

    /**
     * Update district
     */
    DistrictResponseDTO updateDistrict(Long id, DistrictRequestDTO districtRequestDTO);

    /**
     * Delete district
     */
    void deleteDistrict(Long id);

    /**
     * Map District entity to DTO
     */
    DistrictResponseDTO mapToDTO(District district);

    /**
     * Map DTO to District entity
     */
    District mapToEntity(DistrictRequestDTO districtRequestDTO);
}
