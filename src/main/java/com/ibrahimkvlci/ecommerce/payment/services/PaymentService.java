package com.ibrahimkvlci.ecommerce.payment.services;

import com.ibrahimkvlci.ecommerce.payment.dto.CardCheckDTO;

public interface PaymentService {

    String payCheck(CardCheckDTO paymentDTO);
}
