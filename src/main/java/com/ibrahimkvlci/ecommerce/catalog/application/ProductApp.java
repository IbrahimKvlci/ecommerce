package com.ibrahimkvlci.ecommerce.catalog.application;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDTO;
import com.ibrahimkvlci.ecommerce.catalog.services.ProductService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductApp {

    private final ProductService productService;

    public ProductDTO getProductById(Long productId) {
        var result = productService.getProductById(productId);
        if (result.isSuccess() && result.getData() != null) {
            return productService.mapToDTO(result.getData());
        }
        return null;
    }

    public boolean isProductAvailable(Long productId) {
        return productService.isProductAvailable(productId);
    }
}
