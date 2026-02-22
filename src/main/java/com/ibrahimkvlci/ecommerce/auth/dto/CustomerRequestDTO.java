package com.ibrahimkvlci.ecommerce.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;

}
