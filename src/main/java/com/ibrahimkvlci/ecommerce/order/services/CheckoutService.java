package com.ibrahimkvlci.ecommerce.order.services;

import com.ibrahimkvlci.ecommerce.order.dto.OrderDTO;

public interface CheckoutService {

    OrderDTO checkout(Long cartId);
}
