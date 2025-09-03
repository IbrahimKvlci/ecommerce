package com.ibrahimkvlci.ecommerce.auth.services;

import com.ibrahimkvlci.ecommerce.auth.dto.AuthRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.AuthResponse;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerRequest;

public interface AuthService {

    AuthResponse login(AuthRequest request);

    AuthResponse registerAsCustomer(RegisterCustomerRequest request);
}


