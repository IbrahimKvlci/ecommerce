package com.ibrahimkvlci.ecommerce.auth.services;

import com.ibrahimkvlci.ecommerce.auth.dto.RegisterAdminRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterAdminResponse;
import com.ibrahimkvlci.ecommerce.auth.exceptions.AuthException;
import com.ibrahimkvlci.ecommerce.auth.exceptions.RegistrationException;
import com.ibrahimkvlci.ecommerce.auth.models.Admin;
import com.ibrahimkvlci.ecommerce.auth.models.Role;
import com.ibrahimkvlci.ecommerce.auth.repositories.AdminRepository;
import com.ibrahimkvlci.ecommerce.auth.repositories.RoleRepository;
import com.ibrahimkvlci.ecommerce.auth.repositories.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.HashSet;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public RegisterAdminResponse registerAdmin(RegisterAdminRequest request) {
        if (userInfoRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new RegistrationException("Email already in use");
        }

        Admin admin = new Admin();
        admin.setEmail(request.getEmail());
        admin.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new AuthException("Role ADMIN not configured"));
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        admin.setRoles(roles);

        Admin saved = adminRepository.save(admin);
        log.info("Admin registered successfully with email: {}", saved.getEmail());

        return new RegisterAdminResponse(saved.getEmail());
    }

}
