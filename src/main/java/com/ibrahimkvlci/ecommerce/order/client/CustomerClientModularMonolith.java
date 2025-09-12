package com.ibrahimkvlci.ecommerce.order.client;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.order.OrderBus;
import com.ibrahimkvlci.ecommerce.order.dto.CustomerDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerClientModularMonolith implements CustomerClient {

    private final OrderBus orderBus;

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return orderBus.getCustomerById(id);
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        return orderBus.getCustomerByEmail(email);
    }

    @Override
    public boolean existsById(Long id) {
        return orderBus.existsCustomerById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return orderBus.existsCustomerByEmail(email);
    }
}
