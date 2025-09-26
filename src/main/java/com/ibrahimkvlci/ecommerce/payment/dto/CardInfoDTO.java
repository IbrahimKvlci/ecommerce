package com.ibrahimkvlci.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardInfoDTO {

    @NotBlank(message = "Card number is required")
    @Size(min = 12, max = 19, message = "Card number length must be between 12 and 19")
    private String cardNumber;

    @NotBlank(message = "Card holder name is required")
    private String cardHolderName;

    @NotBlank(message = "Expiration year is required")
    private String expirationDateYear;

    @NotBlank(message = "Expiration month is required")
    private String expirationDateMonth;

    @NotBlank(message = "CVV is required")
    @Size(min = 3, max = 4, message = "CVV must be 3 or 4 digits")
    private String cvv;
}
