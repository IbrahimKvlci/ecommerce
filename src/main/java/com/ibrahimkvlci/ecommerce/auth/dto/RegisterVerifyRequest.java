package com.ibrahimkvlci.ecommerce.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVerifyRequest {
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Code is required")
    private String code;
}
