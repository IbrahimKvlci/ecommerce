package com.ibrahimkvlci.ecommerce.address.controllers;

import com.ibrahimkvlci.ecommerce.address.dto.NeighborhoodRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.NeighborhoodResponseDTO;
import com.ibrahimkvlci.ecommerce.address.services.NeighborhoodService;
import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;
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
    public ResponseEntity<DataResult<NeighborhoodResponseDTO>> createNeighborhood(
            @Valid @RequestBody NeighborhoodRequestDTO neighborhoodRequestDTO) {
        return new ResponseEntity<>(neighborhoodService.createNeighborhood(neighborhoodRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<DataResult<List<NeighborhoodResponseDTO>>> getAllNeighborhoods() {
        return ResponseEntity.ok(neighborhoodService.getAllNeighborhoods());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<NeighborhoodResponseDTO>> getNeighborhoodById(@PathVariable Long id) {
        return ResponseEntity.ok(neighborhoodService.getNeighborhoodById(id));
    }

    @GetMapping("/district/{districtId}")
    public ResponseEntity<DataResult<List<NeighborhoodResponseDTO>>> getNeighborhoodsByDistrictId(
            @PathVariable Long districtId) {
        return ResponseEntity.ok(neighborhoodService.getNeighborhoodsByDistrictId(districtId));
    }

    @GetMapping("/search")
    public ResponseEntity<DataResult<List<NeighborhoodResponseDTO>>> searchNeighborhoodsByName(
            @RequestParam String name) {
        return ResponseEntity.ok(neighborhoodService.searchNeighborhoodsByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResult<NeighborhoodResponseDTO>> updateNeighborhood(@PathVariable Long id,
            @Valid @RequestBody NeighborhoodRequestDTO neighborhoodRequestDTO) {
        return ResponseEntity.ok(neighborhoodService.updateNeighborhood(id, neighborhoodRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteNeighborhood(@PathVariable Long id) {
        return ResponseEntity.ok(neighborhoodService.deleteNeighborhood(id));
    }
}
