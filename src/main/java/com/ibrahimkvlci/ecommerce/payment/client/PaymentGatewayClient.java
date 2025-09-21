package com.ibrahimkvlci.ecommerce.payment.client;

public interface PaymentGatewayClient {

    String cardCheck(String xmlString);

    String pay(String xmlString);
}
