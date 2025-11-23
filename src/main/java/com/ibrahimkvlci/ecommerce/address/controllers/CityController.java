package com.ibrahimkvlci.ecommerce.address.controllers;

import com.ibrahimkvlci.ecommerce.address.dto.CityRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CityResponseDTO;
import com.ibrahimkvlci.ecommerce.address.services.CityService;
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
    public ResponseEntity<CityResponseDTO> createCity(@Valid @RequestBody CityRequestDTO cityRequestDTO) {
        CityResponseDTO createdDTO = cityService.createCity(cityRequestDTO);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CityResponseDTO>> getAllCities() {
        List<CityResponseDTO> cityDTOs = cityService.getAllCities();
        return ResponseEntity.ok(cityDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponseDTO> getCityById(@PathVariable Long id) {
        return cityService.getCityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<List<CityResponseDTO>> getCitiesByCountryId(@PathVariable Long countryId) {
        List<CityResponseDTO> cityDTOs = cityService.getCitiesByCountryId(countryId);
        return ResponseEntity.ok(cityDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CityResponseDTO>> searchCitiesByName(@RequestParam String name) {
        List<CityResponseDTO> cityDTOs = cityService.searchCitiesByName(name);
        return ResponseEntity.ok(cityDTOs);
    }

    @GetMapping("/search/country")
    public ResponseEntity<List<CityResponseDTO>> searchCitiesByCountryAndName(
            @RequestParam Long countryId,
            @RequestParam String name) {
        List<CityResponseDTO> cityDTOs = cityService.searchCitiesByCountryAndName(countryId, name);
        return ResponseEntity.ok(cityDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponseDTO> updateCity(@PathVariable Long id,
            @Valid @RequestBody CityRequestDTO cityRequestDTO) {
        CityResponseDTO updatedDTO = cityService.updateCity(id, cityRequestDTO);
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
