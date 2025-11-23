package com.ibrahimkvlci.ecommerce.address.controllers;

import com.ibrahimkvlci.ecommerce.address.dto.DistrictRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.DistrictResponseDTO;
import com.ibrahimkvlci.ecommerce.address.services.DistrictService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for District operations
 */
@RestController
@RequestMapping("/api/address/districts")
@CrossOrigin(origins = "*")
public class DistrictController {

    private final DistrictService districtService;

    @Autowired
    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @PostMapping
    public ResponseEntity<DistrictResponseDTO> createDistrict(
            @Valid @RequestBody DistrictRequestDTO districtRequestDTO) {
        DistrictResponseDTO createdDTO = districtService.createDistrict(districtRequestDTO);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DistrictResponseDTO>> getAllDistricts() {
        List<DistrictResponseDTO> districtDTOs = districtService.getAllDistricts();
        return ResponseEntity.ok(districtDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistrictResponseDTO> getDistrictById(@PathVariable Long id) {
        return districtService.getDistrictById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<DistrictResponseDTO>> getDistrictsByCityId(@PathVariable Long cityId) {
        List<DistrictResponseDTO> districtDTOs = districtService.getDistrictsByCityId(cityId);
        return ResponseEntity.ok(districtDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DistrictResponseDTO>> searchDistrictsByName(@RequestParam String name) {
        List<DistrictResponseDTO> districtDTOs = districtService.searchDistrictsByName(name);
        return ResponseEntity.ok(districtDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistrictResponseDTO> updateDistrict(@PathVariable Long id,
            @Valid @RequestBody DistrictRequestDTO districtRequestDTO) {
        DistrictResponseDTO updatedDTO = districtService.updateDistrict(id, districtRequestDTO);
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        districtService.deleteDistrict(id);
        return ResponseEntity.noContent().build();
    }
}
