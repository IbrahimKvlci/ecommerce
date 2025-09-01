package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.CategoryNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.CategoryValidationException;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import com.ibrahimkvlci.ecommerce.catalog.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;

    
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        log.info("Creating new category: {}", categoryDTO.getName());
        
        // Check if category with same name already exists
        if (categoryRepository.existsByNameIgnoreCase(categoryDTO.getName())) {
            throw new CategoryValidationException("Category with name '" + categoryDTO.getName() + "' already exists");
        }
        
        Category category = categoryDTO.toEntity();
        Category savedCategory = categoryRepository.save(category);
        
        log.info("Category created successfully with ID: {}", savedCategory.getId());
        return CategoryDTO.fromEntity(savedCategory);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        log.info("Retrieving all categories");
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        log.info("Retrieving category by ID: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
        return CategoryDTO.fromEntity(category);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryByName(String name) {
        log.info("Retrieving category by name: {}", name);
        Category category = categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with name: " + name));
        return CategoryDTO.fromEntity(category);
    }
    
    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        log.info("Updating category with ID: {}", id);
        
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
        
        // Check if another category with the same name exists (excluding current category)
        if (!existingCategory.getName().equalsIgnoreCase(categoryDTO.getName()) 
            && categoryRepository.existsByNameIgnoreCase(categoryDTO.getName())) {
            throw new CategoryValidationException("Category with name '" + categoryDTO.getName() + "' already exists");
        }
        
        existingCategory.setName(categoryDTO.getName());
        
        Category updatedCategory = categoryRepository.save(existingCategory);
        
        log.info("Category updated successfully with ID: {}", updatedCategory.getId());
        return CategoryDTO.fromEntity(updatedCategory);
    }
    
    @Override
    public void deleteCategory(Long id) {
        log.info("Deleting category with ID: {}", id);
        
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found with ID: " + id);
        }
        
        categoryRepository.deleteById(id);
        log.info("Category deleted successfully with ID: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> searchCategoriesByName(String name) {
        log.info("Searching categories by name: {}", name);
        return categoryRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }
}
