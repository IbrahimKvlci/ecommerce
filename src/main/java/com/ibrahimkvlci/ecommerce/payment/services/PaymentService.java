package com.ibrahimkvlci.ecommerce.payment.services;

import com.ibrahimkvlci.ecommerce.payment.dto.PaymentDTO;

public interface PaymentService {

    void pay(PaymentDTO paymentDTO);
}
