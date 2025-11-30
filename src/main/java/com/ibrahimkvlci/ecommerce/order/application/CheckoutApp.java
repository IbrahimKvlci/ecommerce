package com.ibrahimkvlci.ecommerce.order.application;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.order.dto.SaleResponse;
import com.ibrahimkvlci.ecommerce.order.services.CheckoutService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CheckoutApp {

    private final CheckoutService checkoutService;

    public SaleResponse okCheckout(SaleResponse response) {

        return checkoutService.okCheckout(response).getData();
    }

    public SaleResponse failCheckout(SaleResponse response) {
        return checkoutService.failCheckout(response).getData();
    }
}
