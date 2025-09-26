package com.ibrahimkvlci.ecommerce.order.client;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.ibrahimkvlci.ecommerce.order.dto.SaleRequest;
import com.ibrahimkvlci.ecommerce.order.dto.SaleResponse;

public interface PaymentClient {

    SaleResponse sale(SaleRequest saleRequest)throws NoSuchAlgorithmException,InvalidKeyException;
}
