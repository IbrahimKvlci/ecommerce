package com.ibrahimkvlci.ecommerce.address.controllers;

import com.ibrahimkvlci.ecommerce.address.dto.NeighborhoodRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.NeighborhoodResponseDTO;
import com.ibrahimkvlci.ecommerce.address.services.NeighborhoodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Neighborhood operations
 */
@RestController
@RequestMapping("/api/address/neighborhoods")
@CrossOrigin(origins = "*")
public class NeighborhoodController {

    private final NeighborhoodService neighborhoodService;

    @Autowired
    public NeighborhoodController(NeighborhoodService neighborhoodService) {
        this.neighborhoodService = neighborhoodService;
    }

    @PostMapping
    public ResponseEntity<NeighborhoodResponseDTO> createNeighborhood(
            @Valid @RequestBody NeighborhoodRequestDTO neighborhoodRequestDTO) {
        NeighborhoodResponseDTO createdDTO = neighborhoodService.createNeighborhood(neighborhoodRequestDTO);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NeighborhoodResponseDTO>> getAllNeighborhoods() {
        List<NeighborhoodResponseDTO> neighborhoodDTOs = neighborhoodService.getAllNeighborhoods();
        return ResponseEntity.ok(neighborhoodDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NeighborhoodResponseDTO> getNeighborhoodById(@PathVariable Long id) {
        return neighborhoodService.getNeighborhoodById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/district/{districtId}")
    public ResponseEntity<List<NeighborhoodResponseDTO>> getNeighborhoodsByDistrictId(@PathVariable Long districtId) {
        List<NeighborhoodResponseDTO> neighborhoodDTOs = neighborhoodService.getNeighborhoodsByDistrictId(districtId);
        return ResponseEntity.ok(neighborhoodDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<NeighborhoodResponseDTO>> searchNeighborhoodsByName(@RequestParam String name) {
        List<NeighborhoodResponseDTO> neighborhoodDTOs = neighborhoodService.searchNeighborhoodsByName(name);
        return ResponseEntity.ok(neighborhoodDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NeighborhoodResponseDTO> updateNeighborhood(@PathVariable Long id,
            @Valid @RequestBody NeighborhoodRequestDTO neighborhoodRequestDTO) {
        NeighborhoodResponseDTO updatedDTO = neighborhoodService.updateNeighborhood(id, neighborhoodRequestDTO);
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNeighborhood(@PathVariable Long id) {
        neighborhoodService.deleteNeighborhood(id);
        return ResponseEntity.noContent().build();
    }
}
