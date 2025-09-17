package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;

public interface CheckoutService {

    OrderDTO checkoutPending(Long cartId);

    OrderDTO completeCheckout(Long orderId);

}
