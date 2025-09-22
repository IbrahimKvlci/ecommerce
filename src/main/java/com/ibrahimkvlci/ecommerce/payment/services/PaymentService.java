package com.ibrahimkvlci.ecommerce.payment.services;

import com.ibrahimkvlci.ecommerce.payment.dto.PaymentDTO;

public interface PaymentService {

    String payCheck(PaymentDTO paymentDTO);
}
