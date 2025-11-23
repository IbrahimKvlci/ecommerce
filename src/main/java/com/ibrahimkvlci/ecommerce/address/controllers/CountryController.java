package com.ibrahimkvlci.ecommerce.address.controllers;

import com.ibrahimkvlci.ecommerce.address.dto.CountryRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CountryResponseDTO;
import com.ibrahimkvlci.ecommerce.address.services.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Country operations
 */
@RestController
@RequestMapping("/api/address/countries")
@CrossOrigin(origins = "*")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<CountryResponseDTO> createCountry(@Valid @RequestBody CountryRequestDTO countryRequestDTO) {
        CountryResponseDTO createdDTO = countryService.createCountry(countryRequestDTO);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CountryResponseDTO>> getAllCountries() {
        List<CountryResponseDTO> countryDTOs = countryService.getAllCountries();
        return ResponseEntity.ok(countryDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponseDTO> getCountryById(@PathVariable Long id) {
        return countryService.getCountryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CountryResponseDTO> getCountryByName(@PathVariable String name) {
        return countryService.getCountryByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CountryResponseDTO> getCountryByCode(@PathVariable String code) {
        return countryService.getCountryByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CountryResponseDTO>> searchCountriesByName(@RequestParam String name) {
        List<CountryResponseDTO> countryDTOs = countryService.searchCountriesByName(name);
        return ResponseEntity.ok(countryDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryResponseDTO> updateCountry(@PathVariable Long id,
            @Valid @RequestBody CountryRequestDTO countryRequestDTO) {
        CountryResponseDTO updatedDTO = countryService.updateCountry(id, countryRequestDTO);
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }
}
