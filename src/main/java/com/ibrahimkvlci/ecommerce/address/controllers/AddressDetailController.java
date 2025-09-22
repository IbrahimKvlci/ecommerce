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
