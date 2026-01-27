package com.ibrahimkvlci.ecommerce.auth.services;

import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerRequest;
import com.ibrahimkvlci.ecommerce.auth.dto.RegisterCustomerResponse;
import com.ibrahimkvlci.ecommerce.auth.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.auth.dto.CustomerDTO;

import java.util.Optional;

public interface CustomerService {

    DataResult<RegisterCustomerResponse> registerAsCustomer(RegisterCustomerRequest request);

    Optional<CustomerDTO> getCustomerById(Long id);

    Optional<CustomerDTO> getCustomerByEmail(String email);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

}
