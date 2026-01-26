package com.ibrahimkvlci.ecommerce.bus.payment;

import org.springframework.context.ApplicationEvent;

import com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse;

public class PaymentCheckoutFailEvent extends ApplicationEvent {

    private SaleResponse saleResponse;

    public PaymentCheckoutFailEvent(Object source, SaleResponse saleResponse) {
        super(source);
        this.saleResponse = saleResponse;
    }

    public SaleResponse getSaleResponse() {
        return this.saleResponse;
    }

}
