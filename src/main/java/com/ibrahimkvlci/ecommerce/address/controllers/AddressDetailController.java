package com.ibrahimkvlci.ecommerce.address.controllers;

import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailDTO;
import com.ibrahimkvlci.ecommerce.address.models.AddressDetail;
import com.ibrahimkvlci.ecommerce.address.services.AddressDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for AddressDetail operations
 */
@RestController
@RequestMapping("/api/address/address-details")
@CrossOrigin(origins = "*")
public class AddressDetailController {
    
    private final AddressDetailService addressDetailService;
    
    @Autowired
    public AddressDetailController(AddressDetailService addressDetailService) {
        this.addressDetailService = addressDetailService;
    }
    
    @PostMapping
    public ResponseEntity<AddressDetailDTO> createAddressDetail(@Valid @RequestBody AddressDetailDTO addressDetailDTO) {
        AddressDetail addressDetail = addressDetailService.createAddressDetail(addressDetailDTO);
        AddressDetailDTO createdDTO = addressDetailService.mapToDTO(addressDetail);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<AddressDetailDTO>> getAllAddressDetails() {
        List<AddressDetail> addressDetails = addressDetailService.getAllAddressDetails();
        List<AddressDetailDTO> addressDetailDTOs = addressDetails.stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDetailDTOs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AddressDetailDTO> getAddressDetailById(@PathVariable Long id) {
        return addressDetailService.getAddressDetailById(id)
                .map(addressDetail -> ResponseEntity.ok(addressDetailService.mapToDTO(addressDetail)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/country/{countryId}")
    public ResponseEntity<List<AddressDetailDTO>> getAddressDetailsByCountryId(@PathVariable Long countryId) {
        List<AddressDetail> addressDetails = addressDetailService.getAddressDetailsByCountryId(countryId);
        List<AddressDetailDTO> addressDetailDTOs = addressDetails.stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDetailDTOs);
    }
    
    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<AddressDetailDTO>> getAddressDetailsByCityId(@PathVariable Long cityId) {
        List<AddressDetail> addressDetails = addressDetailService.getAddressDetailsByCityId(cityId);
        List<AddressDetailDTO> addressDetailDTOs = addressDetails.stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDetailDTOs);
    }
    
    @GetMapping("/district/{districtId}")
    public ResponseEntity<List<AddressDetailDTO>> getAddressDetailsByDistrictId(@PathVariable Long districtId) {
        List<AddressDetail> addressDetails = addressDetailService.getAddressDetailsByDistrictId(districtId);
        List<AddressDetailDTO> addressDetailDTOs = addressDetails.stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDetailDTOs);
    }
    
    @GetMapping("/neighborhood/{neighborhoodId}")
    public ResponseEntity<List<AddressDetailDTO>> getAddressDetailsByNeighborhoodId(@PathVariable Long neighborhoodId) {
        List<AddressDetail> addressDetails = addressDetailService.getAddressDetailsByNeighborhoodId(neighborhoodId);
        List<AddressDetailDTO> addressDetailDTOs = addressDetails.stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDetailDTOs);
    }
    
    @GetMapping("/search/name")
    public ResponseEntity<List<AddressDetailDTO>> searchAddressDetailsByName(@RequestParam String name) {
        List<AddressDetail> addressDetails = addressDetailService.searchAddressDetailsByName(name);
        List<AddressDetailDTO> addressDetailDTOs = addressDetails.stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDetailDTOs);
    }
    
    @GetMapping("/search/surname")
    public ResponseEntity<List<AddressDetailDTO>> searchAddressDetailsBySurname(@RequestParam String surname) {
        List<AddressDetail> addressDetails = addressDetailService.searchAddressDetailsBySurname(surname);
        List<AddressDetailDTO> addressDetailDTOs = addressDetails.stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDetailDTOs);
    }
    
    @GetMapping("/search/phone")
    public ResponseEntity<List<AddressDetailDTO>> searchAddressDetailsByPhone(@RequestParam String phone) {
        List<AddressDetail> addressDetails = addressDetailService.searchAddressDetailsByPhone(phone);
        List<AddressDetailDTO> addressDetailDTOs = addressDetails.stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDetailDTOs);
    }
    
    @GetMapping("/search/address")
    public ResponseEntity<List<AddressDetailDTO>> searchAddressDetailsByAddress(@RequestParam String address) {
        List<AddressDetail> addressDetails = addressDetailService.searchAddressDetailsByAddress(address);
        List<AddressDetailDTO> addressDetailDTOs = addressDetails.stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDetailDTOs);
    }
    
    @GetMapping("/search/name-surname")
    public ResponseEntity<List<AddressDetailDTO>> searchAddressDetailsByNameAndSurname(
            @RequestParam String name, 
            @RequestParam String surname) {
        List<AddressDetail> addressDetails = addressDetailService.searchAddressDetailsByNameAndSurname(name, surname);
        List<AddressDetailDTO> addressDetailDTOs = addressDetails.stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDetailDTOs);
    }
    
    @GetMapping("/search/location")
    public ResponseEntity<List<AddressDetailDTO>> getAddressDetailsByCountryAndCity(
            @RequestParam Long countryId, 
            @RequestParam Long cityId) {
        List<AddressDetail> addressDetails = addressDetailService.getAddressDetailsByCountryAndCity(countryId, cityId);
        List<AddressDetailDTO> addressDetailDTOs = addressDetails.stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDetailDTOs);
    }
    
    @GetMapping("/search/location-district")
    public ResponseEntity<List<AddressDetailDTO>> getAddressDetailsByCountryCityAndDistrict(
            @RequestParam Long countryId, 
            @RequestParam Long cityId, 
            @RequestParam Long districtId) {
        List<AddressDetail> addressDetails = addressDetailService.getAddressDetailsByCountryCityAndDistrict(countryId, cityId, districtId);
        List<AddressDetailDTO> addressDetailDTOs = addressDetails.stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addressDetailDTOs);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AddressDetailDTO> updateAddressDetail(@PathVariable Long id, 
                                                              @Valid @RequestBody AddressDetailDTO addressDetailDTO) {
        AddressDetail updatedAddressDetail = addressDetailService.updateAddressDetail(id, addressDetailDTO);
        AddressDetailDTO updatedDTO = addressDetailService.mapToDTO(updatedAddressDetail);
        return ResponseEntity.ok(updatedDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddressDetail(@PathVariable Long id) {
        addressDetailService.deleteAddressDetail(id);
        return ResponseEntity.noContent().build();
    }
}
