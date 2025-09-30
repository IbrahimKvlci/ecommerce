package com.ibrahimkvlci.ecommerce.payment.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.ibrahimkvlci.ecommerce.payment.dto.SaleRequest;
import com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse;

public interface PaymentService {

    SaleResponse sale(SaleRequest saleRequest) throws NoSuchAlgorithmException, InvalidKeyException;

    SaleResponse sale3DPay(SaleRequest saleRequest);
}
