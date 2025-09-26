package com.ibrahimkvlci.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequest {

    @NotNull(message = "Card info is required")
    @Valid
    private CardInfoDTO cardInfoDTO;

    @NotNull(message = "Order info is required")
    @Valid
    private OrderDTO orderDTO;

    @NotNull(message = "Customer info is required")
    @Valid
    private CustomerDTO customerDTO;
}
