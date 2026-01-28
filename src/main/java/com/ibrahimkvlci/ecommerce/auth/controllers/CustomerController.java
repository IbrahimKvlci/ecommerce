package com.ibrahimkvlci.ecommerce.auth.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerResponse;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterVerifyRequest;
import com.ibrahimkvlci.ecommerce.auth.services.CustomerService;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.DataResult;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth/customer")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<DataResult<RegisterCustomerResponse>> registerInitiate(
            @Valid @RequestBody RegisterCustomerRequest request) {
        log.info("Customer registration attempt for {}", request.getEmail());
        DataResult<RegisterCustomerResponse> response = customerService.registerInitiate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<DataResult<RegisterCustomerResponse>> registerVerify(
            @Valid @RequestBody RegisterVerifyRequest request) {
        log.info("Customer verification attempt for {}", request.getEmail());
        DataResult<RegisterCustomerResponse> response = customerService.registerComplete(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
