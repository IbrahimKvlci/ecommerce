package com.ibrahimkvlci.ecommerce.auth.services;

import com.ibrahimkvlci.ecommerce.auth.dto.AuthRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.AuthResponse;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerResponse;

public interface AuthService {

    AuthResponse login(AuthRequest request);

    RegisterCustomerResponse registerAsCustomer(RegisterCustomerRequest request);
}


