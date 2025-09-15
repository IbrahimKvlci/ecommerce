package com.ibrahimkvlci.ecommerce.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartRequest {
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
}
