package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.NeighborhoodRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.NeighborhoodResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.Neighborhood;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Neighborhood operations
 */
public interface NeighborhoodService {

    /**
     * Create a new neighborhood
     */
    NeighborhoodResponseDTO createNeighborhood(NeighborhoodRequestDTO neighborhoodRequestDTO);

    /**
     * Get neighborhood by ID
     */
    Optional<NeighborhoodResponseDTO> getNeighborhoodById(Long id);

    /**
     * Get all neighborhoods
     */
    List<NeighborhoodResponseDTO> getAllNeighborhoods();

    /**
     * Get neighborhoods by district ID
     */
    List<NeighborhoodResponseDTO> getNeighborhoodsByDistrictId(Long districtId);

    /**
     * Search neighborhoods by name
     */
    List<NeighborhoodResponseDTO> searchNeighborhoodsByName(String name);

    /**
     * Update neighborhood
     */
    NeighborhoodResponseDTO updateNeighborhood(Long id, NeighborhoodRequestDTO neighborhoodRequestDTO);

    /**
     * Delete neighborhood
     */
    void deleteNeighborhood(Long id);

    /**
     * Map Neighborhood entity to DTO
     */
    NeighborhoodResponseDTO mapToDTO(Neighborhood neighborhood);

    /**
     * Map DTO to Neighborhood entity
     */
    Neighborhood mapToEntity(NeighborhoodRequestDTO neighborhoodRequestDTO);
}
