package com.ibrahimkvlci.ecommerce.bus.order;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.auth.CustomerAppBus;
import com.ibrahimkvlci.ecommerce.order.client.CustomerClient;
import com.ibrahimkvlci.ecommerce.order.dto.CustomerDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderCustomerClientBus implements CustomerClient {

    private final CustomerAppBus customerBus;

    @Override
    public CustomerDTO getCustomerById(Long customerId){
        return new CustomerDTO(
            customerBus.getCustomerById(customerId).getId(),
            customerBus.getCustomerById(customerId).getEmail(),
            customerBus.getCustomerById(customerId).getName(),
            customerBus.getCustomerById(customerId).getSurname(),
            null
        );
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email){
        return new CustomerDTO(
            customerBus.getCustomerByEmail(email).getId(),
            customerBus.getCustomerByEmail(email).getEmail(),
            customerBus.getCustomerByEmail(email).getName(),
            customerBus.getCustomerByEmail(email).getSurname(),
            null
        );
    }

    @Override
    public boolean existsById(Long customerId){
        return customerBus.existsById(customerId);
    }

    @Override
    public boolean existsByEmail(String email){
        return customerBus.existsByEmail(email);
    }

}
