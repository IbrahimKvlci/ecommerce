package com.ibrahimkvlci.ecommerce.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for sale requests in the order module.
 * Used when processing sales transactions for orders.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequest {

    @NotNull(message = "Card info is required")
    @Valid
    private CardCheckDTO cardCheckDTO;

    @NotNull(message = "Order info is required")
    @Valid
    private OrderDTO orderDTO;

    @NotNull(message = "Customer info is required")
    @Valid
    private CustomerDTO customerDTO;
}
