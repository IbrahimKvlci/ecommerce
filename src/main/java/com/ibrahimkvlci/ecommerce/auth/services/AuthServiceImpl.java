package com.ibrahimkvlci.ecommerce.auth.services;

import com.ibrahimkvlci.ecommerce.auth.dto.AuthRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.AuthResponse;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerResponse;
import com.ibrahimkvlci.ecommerce.auth.exceptions.AuthException;
import com.ibrahimkvlci.ecommerce.auth.models.Customer;
import com.ibrahimkvlci.ecommerce.auth.models.Role;
import com.ibrahimkvlci.ecommerce.auth.models.User;
import com.ibrahimkvlci.ecommerce.auth.models.Role.RoleEnum;
import com.ibrahimkvlci.ecommerce.auth.repositories.CustomerRepository;
import com.ibrahimkvlci.ecommerce.auth.repositories.RoleRepository;
import com.ibrahimkvlci.ecommerce.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmailIgnoreCaseWithRoles(request.getEmail())
               .orElseThrow(() -> new AuthException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new AuthException("Invalid credentials");
        }

        String token=jwtService.generateToken(request.getEmail());
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
        Role customerRole = roleRepository.findByName(RoleEnum.CUSTOMER)
                .orElseThrow(() -> new AuthException("Role CUSTOMER not configured"));
        customer.setRoles(Set.of(customerRole));

        Customer saved = customerRepository.save(customer);

        return new RegisterCustomerResponse(saved.getEmail(), saved.getName(), saved.getSurname(), saved.getPhoneNumber());
    }
}


