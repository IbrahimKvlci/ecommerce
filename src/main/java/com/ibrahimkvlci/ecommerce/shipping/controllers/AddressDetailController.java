package com.ibrahimkvlci.ecommerce.shipping.controllers;

import com.ibrahimkvlci.ecommerce.shipping.dto.AddressDetailDTO;
import com.ibrahimkvlci.ecommerce.shipping.services.AddressDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for AddressDetail operations.
 * Provides endpoints for CRUD operations and address management.
 */
@RestController
@RequestMapping("/api/shipping/addresses")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AddressDetailController {
    
    private final AddressDetailService addressDetailService;
    
    /**
     * Create a new address detail
     */
    @PostMapping
    public ResponseEntity<AddressDetailDTO> createAddressDetail(@Valid @RequestBody AddressDetailDTO addressDetailDTO) {
        AddressDetailDTO createdAddressDetail = addressDetailService.createAddressDetail(addressDetailDTO);
        return new ResponseEntity<>(createdAddressDetail, HttpStatus.CREATED);
    }
    
    /**
     * Get all address details
     */
    @GetMapping
    public ResponseEntity<List<AddressDetailDTO>> getAllAddressDetails() {
        List<AddressDetailDTO> addressDetails = addressDetailService.getAllAddressDetails();
        return ResponseEntity.ok(addressDetails);
    }
    
    /**
     * Get address detail by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressDetailDTO> getAddressDetailById(@PathVariable Long id) {
        Optional<AddressDetailDTO> addressDetail = addressDetailService.getAddressDetailById(id);
        return addressDetail.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Update an existing address detail
     */
    @PutMapping("/{id}")
    public ResponseEntity<AddressDetailDTO> updateAddressDetail(
            @PathVariable Long id, 
            @Valid @RequestBody AddressDetailDTO addressDetailDTO) {
        AddressDetailDTO updatedAddressDetail = addressDetailService.updateAddressDetail(id, addressDetailDTO);
        return ResponseEntity.ok(updatedAddressDetail);
    }
    
    /**
     * Delete an address detail
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddressDetail(@PathVariable Long id) {
        addressDetailService.deleteAddressDetail(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Get all addresses for a specific customer
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AddressDetailDTO>> getAddressDetailsByCustomerId(@PathVariable Long customerId) {
        List<AddressDetailDTO> addressDetails = addressDetailService.getAddressDetailsByCustomerId(customerId);
        return ResponseEntity.ok(addressDetails);
    }
    
    /**
     * Get addresses by city
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<List<AddressDetailDTO>> getAddressDetailsByCity(@PathVariable String city) {
        List<AddressDetailDTO> addressDetails = addressDetailService.getAddressDetailsByCity(city);
        return ResponseEntity.ok(addressDetails);
    }
    
    /**
     * Get addresses by state
     */
    @GetMapping("/state/{state}")
    public ResponseEntity<List<AddressDetailDTO>> getAddressDetailsByState(@PathVariable String state) {
        List<AddressDetailDTO> addressDetails = addressDetailService.getAddressDetailsByState(state);
        return ResponseEntity.ok(addressDetails);
    }
    
    /**
     * Get addresses by ZIP code
     */
    @GetMapping("/zip/{zipCode}")
    public ResponseEntity<List<AddressDetailDTO>> getAddressDetailsByZipCode(@PathVariable String zipCode) {
        List<AddressDetailDTO> addressDetails = addressDetailService.getAddressDetailsByZipCode(zipCode);
        return ResponseEntity.ok(addressDetails);
    }
    
    /**
     * Get addresses by city and state
     */
    @GetMapping("/city/{city}/state/{state}")
    public ResponseEntity<List<AddressDetailDTO>> getAddressDetailsByCityAndState(
            @PathVariable String city, 
            @PathVariable String state) {
        List<AddressDetailDTO> addressDetails = addressDetailService.getAddressDetailsByCityAndState(city, state);
        return ResponseEntity.ok(addressDetails);
    }
    
    /**
     * Get default address for a customer
     */
    @GetMapping("/customer/{customerId}/default")
    public ResponseEntity<AddressDetailDTO> getDefaultAddressByCustomerId(@PathVariable Long customerId) {
        Optional<AddressDetailDTO> addressDetail = addressDetailService.getDefaultAddressByCustomerId(customerId);
        return addressDetail.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
