package com.ibrahimkvlci.ecommerce.address.controllers;

import com.ibrahimkvlci.ecommerce.address.dto.DistrictRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.DistrictResponseDTO;
import com.ibrahimkvlci.ecommerce.address.services.DistrictService;
import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;
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
    public ResponseEntity<DataResult<DistrictResponseDTO>> createDistrict(
            @Valid @RequestBody DistrictRequestDTO districtRequestDTO) {
        return new ResponseEntity<>(districtService.createDistrict(districtRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<DataResult<List<DistrictResponseDTO>>> getAllDistricts() {
        return ResponseEntity.ok(districtService.getAllDistricts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<DistrictResponseDTO>> getDistrictById(@PathVariable Long id) {
        return ResponseEntity.ok(districtService.getDistrictById(id));
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<DataResult<List<DistrictResponseDTO>>> getDistrictsByCityId(@PathVariable Long cityId) {
        return ResponseEntity.ok(districtService.getDistrictsByCityId(cityId));
    }

    @GetMapping("/search")
    public ResponseEntity<DataResult<List<DistrictResponseDTO>>> searchDistrictsByName(@RequestParam String name) {
        return ResponseEntity.ok(districtService.searchDistrictsByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResult<DistrictResponseDTO>> updateDistrict(@PathVariable Long id,
            @Valid @RequestBody DistrictRequestDTO districtRequestDTO) {
        return ResponseEntity.ok(districtService.updateDistrict(id, districtRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteDistrict(@PathVariable Long id) {
        return ResponseEntity.ok(districtService.deleteDistrict(id));
    }
}
