package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.CheckoutRequestDTO;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.dto.SaleResponse;
import com.ibrahimkvlci.ecommerce.order.utils.RequestUtils;

public interface CheckoutService {

    SaleResponse checkoutPending(CheckoutRequestDTO request,String clientIp,RequestUtils.ClientType clientType);

    SaleResponse checkoutPending3D(CheckoutRequestDTO request,String clientIp,RequestUtils.ClientType clientType);

    SaleResponse okCheckout(SaleResponse response);
    
    SaleResponse failCheckout(SaleResponse response);

    OrderDTO completeCheckout(Long orderId);

}
