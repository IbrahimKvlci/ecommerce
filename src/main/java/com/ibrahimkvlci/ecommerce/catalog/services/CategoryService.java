package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.CategoryDTO;

import java.util.List;

/**
 * Service interface for Category operations.
 */
public interface CategoryService {
    
    /**
     * Create a new category
     */
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    
    /**
     * Get all categories
     */
    List<CategoryDTO> getAllCategories();
    
    /**
     * Get category by ID
     */
    CategoryDTO getCategoryById(Long id);
    
    /**
     * Get category by name
     */
    CategoryDTO getCategoryByName(String name);
    
    /**
     * Update an existing category
     */
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    
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
}
