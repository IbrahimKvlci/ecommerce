package com.ibrahimkvlci.ecommerce.auth.services;

import org.springframework.stereotype.Service;

import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerResponse;
import com.ibrahimkvlci.ecommerce.auth.dto.CustomerDTO;

import java.util.Optional;

@Service
public interface CustomerService {
    
    RegisterCustomerResponse registerAsCustomer(RegisterCustomerRequest request);
    
    Optional<CustomerDTO> getCustomerById(Long id);
    
    Optional<CustomerDTO> getCustomerByEmail(String email);
    
    boolean existsById(Long id);
    
    boolean existsByEmail(String email);
}
