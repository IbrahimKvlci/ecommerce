package com.ibrahimkvlci.ecommerce.address.controllers;

import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailResponseDTO;
import com.ibrahimkvlci.ecommerce.address.services.AddressDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<AddressDetailResponseDTO> createAddressDetail(
            @Valid @RequestBody AddressDetailRequestDTO addressDetailDTO) {
        AddressDetailResponseDTO createdDTO = addressDetailService.createAddressDetail(addressDetailDTO);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AddressDetailResponseDTO>> getAllAddressDetails() {
        List<AddressDetailResponseDTO> addressDetailDTOs = addressDetailService.getAllAddressDetails();
        return ResponseEntity.ok(addressDetailDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDetailResponseDTO> getAddressDetailById(@PathVariable Long id) {
        var res = addressDetailService.getAddressDetailById(id).get();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<AddressDetailResponseDTO>> getAddressDetailsOfCustomer() {
        return ResponseEntity.ok(addressDetailService.getAddressDetailsOfCustomer());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDetailResponseDTO> updateAddressDetail(@PathVariable Long id,
            @Valid @RequestBody AddressDetailRequestDTO addressDetailDTO) {
        var res = addressDetailService.updateAddressDetail(id, addressDetailDTO);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddressDetail(@PathVariable Long id) {
        addressDetailService.deleteAddressDetail(id);
        return ResponseEntity.noContent().build();
    }
}
