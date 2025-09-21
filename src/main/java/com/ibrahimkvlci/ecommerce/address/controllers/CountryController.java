package com.ibrahimkvlci.ecommerce.address.controllers;

import com.ibrahimkvlci.ecommerce.address.dto.CountryDTO;
import com.ibrahimkvlci.ecommerce.address.models.Country;
import com.ibrahimkvlci.ecommerce.address.services.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<CountryDTO> createCountry(@Valid @RequestBody CountryDTO countryDTO) {
        Country country = countryService.createCountry(countryDTO);
        CountryDTO createdDTO = countryService.mapToDTO(country);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        List<Country> countries = countryService.getAllCountries();
        List<CountryDTO> countryDTOs = countries.stream()
                .map(countryService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(countryDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable Long id) {
        return countryService.getCountryById(id)
                .map(country -> ResponseEntity.ok(countryService.mapToDTO(country)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<CountryDTO> getCountryByName(@PathVariable String name) {
        return countryService.getCountryByName(name)
                .map(country -> ResponseEntity.ok(countryService.mapToDTO(country)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<CountryDTO> getCountryByCode(@PathVariable String code) {
        return countryService.getCountryByCode(code)
                .map(country -> ResponseEntity.ok(countryService.mapToDTO(country)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<CountryDTO>> searchCountriesByName(@RequestParam String name) {
        List<Country> countries = countryService.searchCountriesByName(name);
        List<CountryDTO> countryDTOs = countries.stream()
                .map(countryService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(countryDTOs);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable Long id, 
                                                  @Valid @RequestBody CountryDTO countryDTO) {
        Country updatedCountry = countryService.updateCountry(id, countryDTO);
        CountryDTO updatedDTO = countryService.mapToDTO(updatedCountry);
        return ResponseEntity.ok(updatedDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }
}
