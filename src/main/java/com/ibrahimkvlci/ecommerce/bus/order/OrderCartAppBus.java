package com.ibrahimkvlci.ecommerce.bus.order;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.order.application.CartApp;
import com.ibrahimkvlci.ecommerce.order.dto.CartDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CreateCartRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderCartAppBus {

    private final CartApp cartApp;

    public CartDTO createCart(Long customerId) {
        CreateCartRequest request = new CreateCartRequest();
        request.setCustomerId(customerId);
        return cartApp.createCart(request);
    }

    public CartDTO getCartOfCustomer() {
        return cartApp.getCartOfCustomer();
    }
}
