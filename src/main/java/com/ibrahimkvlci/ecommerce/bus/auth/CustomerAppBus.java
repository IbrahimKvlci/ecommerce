package com.ibrahimkvlci.ecommerce.bus.auth;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.auth.application.CustomerApp;
import com.ibrahimkvlci.ecommerce.auth.dto.CustomerDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerAppBus {

    private final CustomerApp customerApp;

    public CustomerDTO getCustomerById(Long id) {
        return customerApp.getCustomerById(id);
    }

    public CustomerDTO getCustomerByEmail(String email) {
        return customerApp.getCustomerByEmail(email);
    }

    public boolean existsById(Long id) {
        return customerApp.existsById(id);
    }

    public boolean existsByEmail(String email) {
        return customerApp.existsByEmail(email);
    }


}
