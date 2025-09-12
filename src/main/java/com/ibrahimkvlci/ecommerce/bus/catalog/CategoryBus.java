package com.ibrahimkvlci.ecommerce.bus.catalog;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.application.CategoryApp;
import com.ibrahimkvlci.ecommerce.catalog.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryBus {

    private final CategoryApp categoryApp;

    public CategoryDTO getCategoryById(Long id){
        return categoryApp.getCategoryById(id);
    }

    public boolean existsById(Long id){
        return categoryApp.existsById(id);
    }
}
