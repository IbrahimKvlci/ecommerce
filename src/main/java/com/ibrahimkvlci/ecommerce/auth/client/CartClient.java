package com.ibrahimkvlci.ecommerce.auth.client;

import com.ibrahimkvlci.ecommerce.auth.dto.CartDTO;

public interface CartClient {

    CartDTO createCart(Long customerId);
}
