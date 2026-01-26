package com.ibrahimkvlci.ecommerce.payment.client;

import com.ibrahimkvlci.ecommerce.payment.dto.SaleResponse;

public interface CheckoutClient {

    void okCheckout(SaleResponse saleResponse);

    void failCheckout(SaleResponse saleResponse);
}
