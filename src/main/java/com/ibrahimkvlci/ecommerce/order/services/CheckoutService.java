package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.CheckoutRequestDTO;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.dto.SaleResponse;
import com.ibrahimkvlci.ecommerce.order.utils.RequestUtils;

public interface CheckoutService {

    SaleResponse checkoutPending(CheckoutRequestDTO request,String clientIp,RequestUtils.ClientType clientType);

    OrderDTO completeCheckout(Long orderId);

}
