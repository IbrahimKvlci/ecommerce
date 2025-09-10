package com.ibrahimkvlci.ecommerce.auth.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterAdminResponse extends RegisterResponseBase {

    public RegisterAdminResponse(String email) {
        super(email);
    }
}
