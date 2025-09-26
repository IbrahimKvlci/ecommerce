package com.ibrahimkvlci.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @NotBlank(message = "Order number is required")
    private String orderNumber;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private String amount;

    @NotBlank(message = "Currency code is required")
    private int currencyCode;

    @NotNull(message = "Install Count is required and must be between 1 and 99")
    @Min(value = 1, message = "Install Count must be at least 1")
    @Max(value = 99, message = "Install Count must be at most 99")
    private Integer installCount;

}
