package com.ibrahimkvlci.ecommerce.auth.client;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.auth.dto.CartDTO;
import com.ibrahimkvlci.ecommerce.bus.auth.CustomerClientBus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CartClientModularMonolith implements CartClient {


    private final CustomerClientBus customerBus;

    @Override
    public CartDTO createCart(Long customerId) {
        return customerBus.createCart(customerId);
    }

}
