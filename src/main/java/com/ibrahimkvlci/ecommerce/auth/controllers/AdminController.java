package com.ibrahimkvlci.ecommerce.auth.controllers;

import com.ibrahimkvlci.ecommerce.auth.dto.RegisterAdminRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterAdminResponse;
import com.ibrahimkvlci.ecommerce.auth.models.Admin;
import com.ibrahimkvlci.ecommerce.auth.services.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/admin")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<RegisterAdminResponse> registerAdmin(@Valid @RequestBody RegisterAdminRequest request) {
        log.info("Admin registration attempt for {}", request.getEmail());
        RegisterAdminResponse response = adminService.registerAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
