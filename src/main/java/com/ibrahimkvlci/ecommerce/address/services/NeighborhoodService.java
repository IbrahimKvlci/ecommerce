package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.NeighborhoodRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.NeighborhoodResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.Neighborhood;

import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;

import java.util.List;

/**
 * Service interface for Neighborhood operations
 */
public interface NeighborhoodService {

    /**
     * Create a new neighborhood
     */
    DataResult<NeighborhoodResponseDTO> createNeighborhood(NeighborhoodRequestDTO neighborhoodRequestDTO);

    /**
     * Get neighborhood by ID
     */
    DataResult<NeighborhoodResponseDTO> getNeighborhoodById(Long id);

    /**
     * Get all neighborhoods
     */
    DataResult<List<NeighborhoodResponseDTO>> getAllNeighborhoods();

    /**
     * Get neighborhoods by district ID
     */
    DataResult<List<NeighborhoodResponseDTO>> getNeighborhoodsByDistrictId(Long districtId);

    /**
     * Search neighborhoods by name
     */
    DataResult<List<NeighborhoodResponseDTO>> searchNeighborhoodsByName(String name);

    /**
     * Update neighborhood
     */
    DataResult<NeighborhoodResponseDTO> updateNeighborhood(Long id, NeighborhoodRequestDTO neighborhoodRequestDTO);

    /**
     * Delete neighborhood
     */
    Result deleteNeighborhood(Long id);

    /**
     * Map Neighborhood entity to DTO
     */
    NeighborhoodResponseDTO mapToDTO(Neighborhood neighborhood);

    /**
     * Map DTO to Neighborhood entity
     */
    Neighborhood mapToEntity(NeighborhoodRequestDTO neighborhoodRequestDTO);
}
