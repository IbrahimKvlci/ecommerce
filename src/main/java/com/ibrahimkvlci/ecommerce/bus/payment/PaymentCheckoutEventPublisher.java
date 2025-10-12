package com.ibrahimkvlci.ecommerce.bus.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.payment.client.CheckoutClient;
import com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentCheckoutEventPublisher implements CheckoutClient{

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void okCheckout(SaleResponse saleResponse) {
        PaymentCheckoutSuccessEvent paymentCheckoutSuccessEvent=new PaymentCheckoutSuccessEvent(this,saleResponse);
        applicationEventPublisher.publishEvent(paymentCheckoutSuccessEvent);
    }

}
