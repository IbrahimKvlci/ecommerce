package com.ibrahimkvlci.ecommerce.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for checkout requests.
 * Used when initiating checkout from a cart.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequestDTO {

    @NotNull
    private String cardNumber;

    @NotNull
    private String cardExpireDateYear;

    @NotNull
    private String cardExpiteDateMonth;

    @NotNull
    private String cardCVV;

    @NotNull
    private String cardHolderName;

    @NotNull
    private Long billAddressId;

    @NotNull
    private Long shipAddressId;

    @NotNull
    @JsonProperty
    private boolean isSecured;

    private String notes;

}
