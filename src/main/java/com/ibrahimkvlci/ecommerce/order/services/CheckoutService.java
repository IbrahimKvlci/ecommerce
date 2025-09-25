package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.CheckoutRequestDTO;
import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;
import com.ibrahimkvlci.ecommerce.order.utils.RequestUtils;

public interface CheckoutService {

    String checkoutPending(CheckoutRequestDTO request,String clientIp,RequestUtils.ClientType clientType);

    OrderDTO completeCheckout(Long orderId);

}
