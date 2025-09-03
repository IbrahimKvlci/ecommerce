package com.ibrahimkvlci.ecommerce.auth.controllers;

import com.ibrahimkvlci.ecommerce.auth.dto.AuthRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.AuthResponse;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerRequest;
import com.ibrahimkvlci.ecommerce.auth.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        log.info("Login attempt for {}", request.getEmail());
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register-customer")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterCustomerRequest request) {
        log.info("Register attempt for {}", request.getEmail());
        AuthResponse response = authService.registerAsCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}


