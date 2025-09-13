package com.ibrahimkvlci.ecommerce.catalog.controllers;

import com.ibrahimkvlci.ecommerce.catalog.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Category operations.
 */
@RestController
@RequestMapping("/api/catalog/categories")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CategoryController {
    
    private final CategoryService categoryService;
    
    /**
     * Create a new category
     */
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        log.info("Creating new category: {}", categoryDTO.getName());
        CategoryDTO createdCategory = categoryService.createCategory(categoryService.mapToEntity(categoryDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
    
    /**
     * Get all categories
     */
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        log.info("Retrieving all categories");
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    
    /**
     * Get category by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        log.info("Retrieving category by ID: {}", id);
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
    
    /**
     * Get category by name
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        log.info("Retrieving category by name: {}", name);
        CategoryDTO category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(category);
    }
    
    /**
     * Update an existing category
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, 
                                                     @Valid @RequestBody CategoryDTO categoryDTO) {
        log.info("Updating category with ID: {}", id);
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryService.mapToEntity(categoryDTO));
        return ResponseEntity.ok(updatedCategory);
    }
    
    /**
     * Delete a category by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.info("Deleting category with ID: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Search categories by name
     */
    @GetMapping("/search/name")
    public ResponseEntity<List<CategoryDTO>> searchCategoriesByName(@RequestParam String name) {
        log.info("Searching categories by name: {}", name);
        List<CategoryDTO> categories = categoryService.searchCategoriesByName(name);
        return ResponseEntity.ok(categories);
    }

    /**
     * Check if category exists by name
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByName(@RequestParam String name) {
        log.info("Checking if category exists by name: {}", name);
        boolean exists = categoryService.existsByName(name);
        return ResponseEntity.ok(exists);
    }
}
