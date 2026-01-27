package com.ibrahimkvlci.ecommerce.auth.services;

import java.util.Set;
import java.util.HashSet;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerResponse;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.auth.client.CartClient;
import com.ibrahimkvlci.ecommerce.auth.dto.CustomerDTO;
import com.ibrahimkvlci.ecommerce.auth.exceptions.RegistrationException;
import com.ibrahimkvlci.ecommerce.auth.models.Customer;
import com.ibrahimkvlci.ecommerce.auth.models.Role;
import com.ibrahimkvlci.ecommerce.auth.repositories.CustomerRepository;
import com.ibrahimkvlci.ecommerce.auth.repositories.RoleRepository;
import com.ibrahimkvlci.ecommerce.auth.repositories.UserInfoRepository;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.DataResult;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final UserInfoRepository userRepository;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final CartClient cartClient;

    @Override
    public DataResult<RegisterCustomerResponse> registerAsCustomer(RegisterCustomerRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new RegistrationException("Email already in use");
        }

        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseThrow(() -> new RegistrationException("Role CUSTOMER not configured"));
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);
        customer.setRoles(roles);

        Customer saved = customerRepository.save(customer);

        cartClient.createCart(saved.getId());

        return new SuccessDataResult<>(
                new RegisterCustomerResponse(saved.getEmail(), saved.getName(), saved.getSurname()));
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long id) {
        return customerRepository.findById(Objects.requireNonNull(id))
                .map(CustomerDTO::fromEntity);
    }

    @Override
    public Optional<CustomerDTO> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(CustomerDTO::fromEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return customerRepository.existsById(Objects.requireNonNull(id));
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

}
