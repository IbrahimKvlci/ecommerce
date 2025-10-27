package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.AddCategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.CategorySubcategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;

import java.util.List;

/**
 * Service interface for Category operations.
 */
public interface CategoryService {
    
    /**
     * Create a new category
     */
    CategoryDTO createCategory(AddCategoryDTO category);
    
    /**
     * Get all categories
     */
    List<CategoryDTO> getAllCategories();
    
    /**
     * Get category by ID
     */
    CategoryDTO getCategoryById(Long id);
    
    List<CategoryDTO> getParentCategories();

    List<CategoryDTO> getSubCategoriesByParentId(Long id);

    List<CategorySubcategoryDTO> getParentCategoryWithSubcategories();

    /**
     * Get category by name
     */
    CategoryDTO getCategoryByName(String name);
    
    /**
     * Update an existing category
     */
    CategoryDTO updateCategory(Long id, Category category);
    
    /**
     * Delete a category by ID
     */
    void deleteCategory(Long id);
    
    /**
     * Search categories by name
     */
    List<CategoryDTO> searchCategoriesByName(String name);
    
    /**
     * Check if category exists by name
     */
    boolean existsByName(String name);

    /**
     * Check if category exists by ID
     */
    boolean existsById(Long id);
    
    /**
     * Convert DTO to entity
     */
    Category mapToEntity(CategoryDTO categoryDTO);
    
    /**
     * Create DTO from entity
     */
    CategoryDTO mapToDTO(Category category);
}
