package com.ibrahimkvlci.ecommerce.auth.mappers;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.auth.dto.CustomerDTO;
import com.ibrahimkvlci.ecommerce.auth.models.Customer;

@Component
public class CustomerMapper {

    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setEmail(customer.getEmail());
        dto.setName(customer.getName());
        dto.setSurname(customer.getSurname());
        dto.setActive(customer.isActive());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());
        return dto;
    }
}
