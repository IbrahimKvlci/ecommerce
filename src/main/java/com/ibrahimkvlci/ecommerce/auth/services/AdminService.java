package com.ibrahimkvlci.ecommerce.auth.services;

import com.ibrahimkvlci.ecommerce.auth.dto.RegisterAdminRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterAdminResponse;


public interface AdminService {
    
    RegisterAdminResponse registerAdmin(RegisterAdminRequest request);

}
