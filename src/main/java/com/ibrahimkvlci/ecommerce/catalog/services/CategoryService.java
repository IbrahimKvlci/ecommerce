package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.AddCategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.CategorySubcategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;

import java.util.List;

/**
 * Service interface for Category operations.
 */
public interface CategoryService {

    /**
     * Create a new category
     */
    DataResult<CategoryDTO> createCategory(AddCategoryDTO category);

    /**
     * Get all categories
     */
    DataResult<List<CategoryDTO>> getAllCategories();

    /**
     * Get category by ID
     */
    DataResult<CategoryDTO> getCategoryById(Long id);

    DataResult<List<CategoryDTO>> getParentCategories();

    DataResult<List<CategoryDTO>> getSubCategoriesByParentId(Long id);

    DataResult<List<CategorySubcategoryDTO>> getParentCategoryWithSubcategories();

    /**
     * Get category by name
     */
    DataResult<CategoryDTO> getCategoryByName(String name);

    /**
     * Update an existing category
     */
    DataResult<CategoryDTO> updateCategory(Long id, Category category);

    /**
     * Delete a category by ID
     */
    Result deleteCategory(Long id);

    /**
     * Search categories by name
     */
    DataResult<List<CategoryDTO>> searchCategoriesByName(String name);

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
