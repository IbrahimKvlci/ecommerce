package com.ibrahimkvlci.ecommerce.bus.payment;

import org.springframework.context.ApplicationEvent;

import com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse;

public class PaymentCheckoutSuccessEvent extends ApplicationEvent{

    private SaleResponse saleResponse;

    public PaymentCheckoutSuccessEvent(Object source,SaleResponse saleResponse) {
        super(source);
        this.saleResponse=saleResponse;
    }

    public SaleResponse getSaleResponse(){
        return this.saleResponse;
    }

}
