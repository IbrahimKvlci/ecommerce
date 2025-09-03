package com.ibrahimkvlci.ecommerce.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterCustomerRequest extends RegisterRequestBase {

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
    
    @NotBlank
    @Size(min = 3, max = 50)
    private String surname;
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number") 
    private String phoneNumber;
}
