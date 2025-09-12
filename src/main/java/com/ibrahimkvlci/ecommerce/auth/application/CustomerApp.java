package com.ibrahimkvlci.ecommerce.auth.application;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.auth.dto.CustomerDTO;
import com.ibrahimkvlci.ecommerce.auth.services.CustomerService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerApp {

    private final CustomerService customerService;

    public CustomerDTO getCustomerById(Long id) {
        return customerService.getCustomerById(id).orElse(null);
    }

    public CustomerDTO getCustomerByEmail(String email) {
        return customerService.getCustomerByEmail(email).orElse(null);
    }

    public boolean existsById(Long id) {
        return customerService.existsById(id);
    }

    public boolean existsByEmail(String email) {
        return customerService.existsByEmail(email);
    }
}
