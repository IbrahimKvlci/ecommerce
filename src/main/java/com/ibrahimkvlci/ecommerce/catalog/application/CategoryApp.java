package com.ibrahimkvlci.ecommerce.catalog.application;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryApp {

    private final CategoryService categoryService;

    public CategoryDTO getCategoryById(Long id){
        return categoryService.getCategoryById(id);
    }

    public boolean existsById(Long id){
        return categoryService.existsById(id);
    }
}
