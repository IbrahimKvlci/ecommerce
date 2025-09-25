package com.ibrahimkvlci.ecommerce.order.client;

import com.ibrahimkvlci.ecommerce.order.dto.CardCheckDTO;

public interface PaymentClient {

    String payCheck(CardCheckDTO cardCheckDTO);
}
