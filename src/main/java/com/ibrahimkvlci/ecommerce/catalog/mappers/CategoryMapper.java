package com.ibrahimkvlci.ecommerce.catalog.mappers;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.dto.AddCategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.CategorySubcategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }

    public CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    public CategorySubcategoryDTO toParentCategorySubcategoryDTO(Category category){
        if (category == null) {
            return null;
        }
        CategorySubcategoryDTO dto =new CategorySubcategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSubcategories(category.getSubCategories().stream().map(this::toDTO).toList());
        return dto;
    }

    public Category fromAddCategoryDTOTOEntity(AddCategoryDTO categoryDTO){
        if (categoryDTO == null) {
            return null;
        }
        Category category = new Category();
        Category parent=new Category();
        parent.setId(categoryDTO.getParentCategoryId());
        category.setName(categoryDTO.getName());
        category.setParent(parent);
        return category;
    }
}


