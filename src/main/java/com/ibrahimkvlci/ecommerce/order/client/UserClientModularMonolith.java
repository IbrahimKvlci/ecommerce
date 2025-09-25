package com.ibrahimkvlci.ecommerce.order.client;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.order.OrderBus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserClientModularMonolith implements UserClient {

    private final OrderBus orderBus;

    @Override
    public Long getCustomerIdFromJWT() {
        return orderBus.getCustomerIdFromJWT();
    }

}
