package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.AddCategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.CategorySubcategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.CategoryNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.CategoryValidationException;
import com.ibrahimkvlci.ecommerce.catalog.mappers.CategoryMapper;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import com.ibrahimkvlci.ecommerce.catalog.repositories.CategoryRepository;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public DataResult<CategoryDTO> createCategory(AddCategoryDTO category) {
        log.info("Creating new category: {}", category.getName());

        // Check if category with same name already exists
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new CategoryValidationException("Category with name '" + category.getName() + "' already exists");
        }
        Category newCategory = categoryMapper.fromAddCategoryDTOTOEntity(category);
        Category savedCategory = categoryRepository.save(Objects.requireNonNull(newCategory));

        log.info("Category created successfully with ID: {}", savedCategory.getId());
        return new SuccessDataResult<>("Category created successfully", this.mapToDTO(savedCategory));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<CategoryDTO>> getAllCategories() {
        log.info("Retrieving all categories");
        return new SuccessDataResult<>("Categories listed successfully",
                categoryRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<CategoryDTO> getCategoryById(Long id) {
        log.info("Retrieving category by ID: {}", id);
        Category category = categoryRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
        return new SuccessDataResult<>("Category found successfully", this.mapToDTO(category));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<CategoryDTO> getCategoryByName(String name) {
        log.info("Retrieving category by name: {}", name);
        Category category = categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with name: " + name));
        return new SuccessDataResult<>("Category found successfully", this.mapToDTO(category));
    }

    @Override
    public DataResult<CategoryDTO> updateCategory(Long id, Category category) {
        log.info("Updating category with ID: {}", id);

        Category existingCategory = categoryRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));

        // Check if another category with the same name exists (excluding current
        // category)
        if (!existingCategory.getName().equalsIgnoreCase(category.getName())
                && categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new CategoryValidationException("Category with name '" + category.getName() + "' already exists");
        }

        existingCategory.setName(category.getName());

        Category updatedCategory = categoryRepository.save(existingCategory);

        log.info("Category updated successfully with ID: {}", updatedCategory.getId());
        return new SuccessDataResult<>("Category updated successfully", this.mapToDTO(updatedCategory));
    }

    @Override
    public Result deleteCategory(Long id) {
        log.info("Deleting category with ID: {}", id);

        if (!categoryRepository.existsById(Objects.requireNonNull(id))) {
            throw new CategoryNotFoundException("Category not found with ID: " + id);
        }

        categoryRepository.deleteById(Objects.requireNonNull(id));
        log.info("Category deleted successfully with ID: {}", id);
        return new SuccessResult("Category deleted successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<CategoryDTO>> searchCategoriesByName(String name) {
        log.info("Searching categories by name: {}", name);
        return new SuccessDataResult<>("Categories found successfully",
                categoryRepository.findByNameContainingIgnoreCase(name).stream().map(this::mapToDTO)
                        .collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(Objects.requireNonNull(id));
    }

    /**
     * Convert DTO to entity
     */
    public Category mapToEntity(CategoryDTO categoryDTO) {
        return categoryMapper.toEntity(categoryDTO);
    }

    /**
     * Create DTO from entity
     */
    public CategoryDTO mapToDTO(Category category) {
        return categoryMapper.toDTO(category);
    }

    @Override
    public DataResult<List<CategoryDTO>> getParentCategories() {
        List<Category> categories = categoryRepository.findByParentIsNull();
        return new SuccessDataResult<>("Parent categories listed successfully",
                categories.stream().map(this::mapToDTO).collect(Collectors.toList()));
    }

    @Override
    public DataResult<List<CategoryDTO>> getSubCategoriesByParentId(Long id) {
        List<Category> categories = categoryRepository.findByParentId(id);
        return new SuccessDataResult<>("Subcategories listed successfully",
                categories.stream().map(this::mapToDTO).collect(Collectors.toList()));
    }

    @Override
    public DataResult<List<CategorySubcategoryDTO>> getParentCategoryWithSubcategories() {
        List<Category> categories = categoryRepository.findByParentIsNull();
        return new SuccessDataResult<>("Parent categories with subcategories listed successfully",
                categories.stream().map(categoryMapper::toParentCategorySubcategoryDTO).toList());
    }
}
