package com.ibrahimkvlci.ecommerce.address.controllers;

import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailResponseDTO;
import com.ibrahimkvlci.ecommerce.address.services.AddressDetailService;
import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;
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
    public ResponseEntity<DataResult<AddressDetailResponseDTO>> createAddressDetail(
            @Valid @RequestBody AddressDetailRequestDTO addressDetailDTO) {
        return new ResponseEntity<>(addressDetailService.createAddressDetail(addressDetailDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<DataResult<List<AddressDetailResponseDTO>>> getAllAddressDetails() {
        return ResponseEntity.ok(addressDetailService.getAllAddressDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<AddressDetailResponseDTO>> getAddressDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(addressDetailService.getAddressDetailById(id));
    }

    @GetMapping("/customer")
    public ResponseEntity<DataResult<List<AddressDetailResponseDTO>>> getAddressDetailsOfCustomer() {
        return ResponseEntity.ok(addressDetailService.getAddressDetailsOfCustomer());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResult<AddressDetailResponseDTO>> updateAddressDetail(@PathVariable Long id,
            @Valid @RequestBody AddressDetailRequestDTO addressDetailDTO) {
        return ResponseEntity.ok(addressDetailService.updateAddressDetail(id, addressDetailDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteAddressDetail(@PathVariable Long id) {
        return ResponseEntity.ok(addressDetailService.deleteAddressDetail(id));
    }
}
