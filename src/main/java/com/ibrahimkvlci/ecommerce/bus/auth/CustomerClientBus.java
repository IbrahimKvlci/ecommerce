package com.ibrahimkvlci.ecommerce.bus.auth;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.auth.dto.CartDTO;
import com.ibrahimkvlci.ecommerce.bus.order.OrderCartAppBus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerClientBus {

    private final OrderCartAppBus cartBus;

    public CartDTO createCart(Long customerId) {
        var cartDTO = cartBus.createCart(customerId);
        return new CartDTO(cartDTO.getId(), customerId);
    }

}
