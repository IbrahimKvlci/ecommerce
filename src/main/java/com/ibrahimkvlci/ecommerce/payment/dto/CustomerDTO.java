package com.ibrahimkvlci.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @NotNull(message = "CustomerId is required")
    private Long customerId;

    @NotBlank(message = "Email address is required")
    @Email(message = "Email address is invalid")
    private String emailAddress;

    @NotBlank(message = "IP address is required")
    private String ipAddress;
    
}
