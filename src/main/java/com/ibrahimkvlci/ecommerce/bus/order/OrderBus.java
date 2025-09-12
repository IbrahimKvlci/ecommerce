package com.ibrahimkvlci.ecommerce.bus.order;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.bus.catalog.CategoryBus;
import com.ibrahimkvlci.ecommerce.bus.catalog.ProductBus;
import com.ibrahimkvlci.ecommerce.bus.auth.CustomerBus;
import com.ibrahimkvlci.ecommerce.order.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.order.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.order.dto.CustomerDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderBus {

    private final ProductBus productBus;
    private final CategoryBus categoryBus;
    private final CustomerBus customerBus;

    public ProductDTO getProductById(Long productId){
        return new ProductDTO(productBus.getProductById(productId).getId(), productBus.getProductById(productId).getTitle());
    }

    public boolean isProductAvailable(Long productId){
        return productBus.isProductAvailable(productId);
    }

    public CategoryDTO getCategoryById(Long categoryId){
        return new CategoryDTO(categoryBus.getCategoryById(categoryId).getId(), categoryBus.getCategoryById(categoryId).getName());
    }

    public boolean existsCategoryById(Long categoryId){
        return categoryBus.existsById(categoryId);
    }

    public CustomerDTO getCustomerById(Long customerId){
        return new CustomerDTO(
            customerBus.getCustomerById(customerId).getId(),
            customerBus.getCustomerById(customerId).getEmail(),
            customerBus.getCustomerById(customerId).getName(),
            customerBus.getCustomerById(customerId).getSurname()
        );
    }

    public CustomerDTO getCustomerByEmail(String email){
        return new CustomerDTO(
            customerBus.getCustomerByEmail(email).getId(),
            customerBus.getCustomerByEmail(email).getEmail(),
            customerBus.getCustomerByEmail(email).getName(),
            customerBus.getCustomerByEmail(email).getSurname()
        );
    }

    public boolean existsCustomerById(Long customerId){
        return customerBus.existsById(customerId);
    }

    public boolean existsCustomerByEmail(String email){
        return customerBus.existsByEmail(email);
    }
}
