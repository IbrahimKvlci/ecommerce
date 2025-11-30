package com.ibrahimkvlci.ecommerce.order.application;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.order.dto.CartDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CreateCartRequest;
import com.ibrahimkvlci.ecommerce.order.services.CartService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CartApp {

    private final CartService cartService;

    public CartDTO createCart(CreateCartRequest request) {
        return cartService.createCart(request).getData();
    }

    public CartDTO getCartByCustomerId(Long id) {
        return cartService.getCartByCustomerId(id).getData();
    }

}