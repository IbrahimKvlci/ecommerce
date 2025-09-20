package com.ibrahimkvlci.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private PaymentDetailDTO paymentDetailDTO;

    private String cardNumber;

    private String cardHolderName;

    private String expirationDate;

    private String cvv;

    
}
