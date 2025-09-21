package com.ibrahimkvlci.ecommerce.address.controllers;

import com.ibrahimkvlci.ecommerce.address.dto.CityDTO;
import com.ibrahimkvlci.ecommerce.address.models.City;
import com.ibrahimkvlci.ecommerce.address.services.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<CityDTO> createCity(@Valid @RequestBody CityDTO cityDTO) {
        City city = cityService.createCity(cityDTO);
        CityDTO createdDTO = cityService.mapToDTO(city);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<CityDTO>> getAllCities() {
        List<City> cities = cityService.getAllCities();
        List<CityDTO> cityDTOs = cities.stream()
                .map(cityService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cityDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCityById(@PathVariable Long id) {
        return cityService.getCityById(id)
                .map(city -> ResponseEntity.ok(cityService.mapToDTO(city)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/country/{countryId}")
    public ResponseEntity<List<CityDTO>> getCitiesByCountryId(@PathVariable Long countryId) {
        List<City> cities = cityService.getCitiesByCountryId(countryId);
        List<CityDTO> cityDTOs = cities.stream()
                .map(cityService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cityDTOs);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<CityDTO>> searchCitiesByName(@RequestParam String name) {
        List<City> cities = cityService.searchCitiesByName(name);
        List<CityDTO> cityDTOs = cities.stream()
                .map(cityService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cityDTOs);
    }
    
    @GetMapping("/search/country")
    public ResponseEntity<List<CityDTO>> searchCitiesByCountryAndName(
            @RequestParam Long countryId, 
            @RequestParam String name) {
        List<City> cities = cityService.searchCitiesByCountryAndName(countryId, name);
        List<CityDTO> cityDTOs = cities.stream()
                .map(cityService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cityDTOs);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> updateCity(@PathVariable Long id, 
                                            @Valid @RequestBody CityDTO cityDTO) {
        City updatedCity = cityService.updateCity(id, cityDTO);
        CityDTO updatedDTO = cityService.mapToDTO(updatedCity);
        return ResponseEntity.ok(updatedDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
