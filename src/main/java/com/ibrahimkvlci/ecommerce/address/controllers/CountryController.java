package com.ibrahimkvlci.ecommerce.address.controllers;

import com.ibrahimkvlci.ecommerce.address.dto.CountryRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CountryResponseDTO;
import com.ibrahimkvlci.ecommerce.address.services.CountryService;
import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;
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
    public ResponseEntity<DataResult<CountryResponseDTO>> createCountry(
            @Valid @RequestBody CountryRequestDTO countryRequestDTO) {
        return new ResponseEntity<>(countryService.createCountry(countryRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<DataResult<List<CountryResponseDTO>>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<CountryResponseDTO>> getCountryById(@PathVariable Long id) {
        return ResponseEntity.ok(countryService.getCountryById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<DataResult<CountryResponseDTO>> getCountryByName(@PathVariable String name) {
        return ResponseEntity.ok(countryService.getCountryByName(name));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<DataResult<CountryResponseDTO>> getCountryByCode(@PathVariable String code) {
        return ResponseEntity.ok(countryService.getCountryByCode(code));
    }

    @GetMapping("/search")
    public ResponseEntity<DataResult<List<CountryResponseDTO>>> searchCountriesByName(@RequestParam String name) {
        return ResponseEntity.ok(countryService.searchCountriesByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResult<CountryResponseDTO>> updateCountry(@PathVariable Long id,
            @Valid @RequestBody CountryRequestDTO countryRequestDTO) {
        return ResponseEntity.ok(countryService.updateCountry(id, countryRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteCountry(@PathVariable Long id) {
        return ResponseEntity.ok(countryService.deleteCountry(id));
    }
}
