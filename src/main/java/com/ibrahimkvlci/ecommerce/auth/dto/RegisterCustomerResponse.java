package com.ibrahimkvlci.ecommerce.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterCustomerResponse extends RegisterResponseBase {

    public RegisterCustomerResponse(String email, String name, String surname) {
        super(email);
        this.name = name;
        this.surname = surname;
    }

    private String name;
    private String surname;

}
