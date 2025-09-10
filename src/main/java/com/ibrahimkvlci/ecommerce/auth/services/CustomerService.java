package com.ibrahimkvlci.ecommerce.auth.services;

import org.springframework.stereotype.Service;

import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerResponse;

@Service
public interface CustomerService {
    
    RegisterCustomerResponse registerAsCustomer(RegisterCustomerRequest request);
}
