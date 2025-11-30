package com.ibrahimkvlci.ecommerce.address.controllers;

import com.ibrahimkvlci.ecommerce.address.dto.CityRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CityResponseDTO;
import com.ibrahimkvlci.ecommerce.address.services.CityService;
import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for City operations
 */
@RestController
@RequestMapping("/api/address/cities")
@CrossOrigin(origins = "*")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<DataResult<CityResponseDTO>> createCity(@Valid @RequestBody CityRequestDTO cityRequestDTO) {
        return new ResponseEntity<>(cityService.createCity(cityRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<DataResult<List<CityResponseDTO>>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<CityResponseDTO>> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<DataResult<List<CityResponseDTO>>> getCitiesByCountryId(@PathVariable Long countryId) {
        return ResponseEntity.ok(cityService.getCitiesByCountryId(countryId));
    }

    @GetMapping("/search")
    public ResponseEntity<DataResult<List<CityResponseDTO>>> searchCitiesByName(@RequestParam String name) {
        return ResponseEntity.ok(cityService.searchCitiesByName(name));
    }

    @GetMapping("/search/country")
    public ResponseEntity<DataResult<List<CityResponseDTO>>> searchCitiesByCountryAndName(
            @RequestParam Long countryId,
            @RequestParam String name) {
        return ResponseEntity.ok(cityService.searchCitiesByCountryAndName(countryId, name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResult<CityResponseDTO>> updateCity(@PathVariable Long id,
            @Valid @RequestBody CityRequestDTO cityRequestDTO) {
        return ResponseEntity.ok(cityService.updateCity(id, cityRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteCity(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.deleteCity(id));
    }
}
