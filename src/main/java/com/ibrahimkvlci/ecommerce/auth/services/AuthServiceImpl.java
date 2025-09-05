package com.ibrahimkvlci.ecommerce.auth.services;

import com.ibrahimkvlci.ecommerce.auth.dto.AuthRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.AuthResponse;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerResponse;
import com.ibrahimkvlci.ecommerce.auth.exceptions.AuthException;
import com.ibrahimkvlci.ecommerce.auth.models.Customer;
import com.ibrahimkvlci.ecommerce.auth.models.Role;
import com.ibrahimkvlci.ecommerce.auth.models.Role.RoleEnum;
import com.ibrahimkvlci.ecommerce.auth.repositories.CustomerRepository;
import com.ibrahimkvlci.ecommerce.auth.repositories.RoleRepository;
import com.ibrahimkvlci.ecommerce.auth.repositories.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.HashSet;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserInfoRepository userRepository;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(AuthRequest request) {
        
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        String token=jwtService.generateToken((UserDetails)authentication.getPrincipal());
        return new AuthResponse(token, "Bearer");
    }

    @Override
    public RegisterCustomerResponse registerAsCustomer(RegisterCustomerRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new AuthException("Email already in use");
        }

        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setPhoneNumber(request.getPhoneNumber());
        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseThrow(() -> new AuthException("Role CUSTOMER not configured"));
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);
        customer.setRoles(roles);

        Customer saved = customerRepository.save(customer);

        return new RegisterCustomerResponse(saved.getEmail(), saved.getName(), saved.getSurname(), saved.getPhoneNumber());
    }
}


