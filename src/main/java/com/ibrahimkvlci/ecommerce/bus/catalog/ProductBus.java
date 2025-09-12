package com.ibrahimkvlci.ecommerce.bus.catalog;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.application.ProductApp;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductBus {

    private final ProductApp productApp;

    public ProductDTO getProductById(Long productId){
        return productApp.getProductById(productId);
    }
    
    public boolean isProductAvailable(Long productId){
        return productApp.isProductAvailable(productId);
    }
}
