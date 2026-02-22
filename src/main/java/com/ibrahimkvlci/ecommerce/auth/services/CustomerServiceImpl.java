package com.ibrahimkvlci.ecommerce.auth.services;

import java.util.Set;
import java.util.HashSet;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerResponse;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterVerifyRequest;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.auth.client.CartClient;
import com.ibrahimkvlci.ecommerce.auth.dto.CustomerDTO;
import com.ibrahimkvlci.ecommerce.auth.dto.CustomerRequestDTO;
import com.ibrahimkvlci.ecommerce.auth.exceptions.RegistrationException;
import com.ibrahimkvlci.ecommerce.auth.models.Customer;
import com.ibrahimkvlci.ecommerce.auth.models.Role;
import com.ibrahimkvlci.ecommerce.auth.models.redis.CustomerCode;
import com.ibrahimkvlci.ecommerce.auth.repositories.CustomerRepository;
import com.ibrahimkvlci.ecommerce.auth.repositories.RoleRepository;
import com.ibrahimkvlci.ecommerce.auth.repositories.UserInfoRepository;
import com.ibrahimkvlci.ecommerce.auth.repositories.redis.CustomerCodeRepository;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.DataResult;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Random;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final UserInfoRepository userRepository;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final CustomerCodeRepository customerCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserInfoService userInfoService;

    private final CartClient cartClient;

    @Override
    public DataResult<RegisterCustomerResponse> registerInitiate(RegisterCustomerRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new RegistrationException("Email already in use");
        }
        if (customerCodeRepository.existsById(request.getEmail())) {
            throw new RegistrationException("Code already sent to email");
        }

        CustomerCode customerCode = new CustomerCode();
        customerCode.setEmail(request.getEmail());
        customerCode.setFirstName(request.getName());
        customerCode.setLastName(request.getSurname());
        customerCode.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        String code = generateCode();
        customerCode.setCode(code);

        CustomerCode saved = customerCodeRepository.save(customerCode);

        emailService.sendVerificationEmail(request.getEmail(), code);

        return new SuccessDataResult<>(
                "Code sent to email", new RegisterCustomerResponse(saved.getEmail(), saved.getFirstName(),
                        saved.getLastName()));
    }

    @Override
    public DataResult<RegisterCustomerResponse> registerComplete(RegisterVerifyRequest request) {
        CustomerCode customerCode = customerCodeRepository.findById(request.getEmail()).get();
        String redisCode = customerCode.getCode();
        if (redisCode == null) {
            throw new RegistrationException("Code time out");
        }
        if (!redisCode.equals(request.getCode())) {
            throw new RegistrationException("Invalid code");
        }

        Customer customer = new Customer();
        customer.setEmail(customerCode.getEmail());
        customer.setPasswordHash(customerCode.getPasswordHash());
        customer.setName(customerCode.getFirstName());
        customer.setSurname(customerCode.getLastName());
        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseThrow(() -> new RegistrationException("Role CUSTOMER not configured"));
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);
        customer.setRoles(roles);

        Customer saved = customerRepository.save(customer);

        cartClient.createCart(saved.getId());
        customerCodeRepository.delete(customerCode);
        return new SuccessDataResult<>(
                "Customer registered successfully",
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

    private String generateCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    @Override
    public DataResult<CustomerDTO> getCustomerInfo() {
        Long userId = userInfoService.getUserIdFromJWT().getData();
        return new SuccessDataResult<>(customerRepository.findById(userId).map(CustomerDTO::fromEntity).get());
    }

    @Override
    public DataResult<CustomerDTO> updateCustomerInfo(CustomerRequestDTO request) {
        if (request.getEmail() == null || request.getName() == null || request.getSurname() == null) {
            throw new RegistrationException("Email, name and surname are required");
        }

        Long userId = userInfoService.getUserIdFromJWT().getData();
        Customer customer = customerRepository.findById(userId).get();
        customer.setEmail(request.getEmail());
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        Customer saved = customerRepository.save(customer);
        return new SuccessDataResult<>(CustomerDTO.fromEntity(saved));
    }

}
