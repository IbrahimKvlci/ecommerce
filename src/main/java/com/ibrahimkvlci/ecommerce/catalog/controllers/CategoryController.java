package com.ibrahimkvlci.ecommerce.catalog.controllers;

import com.ibrahimkvlci.ecommerce.catalog.dto.AddCategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.CategorySubcategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.services.CategoryService;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;
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
    /**
     * Create a new category
     */
    @PostMapping
    public ResponseEntity<DataResult<CategoryDTO>> createCategory(@Valid @RequestBody AddCategoryDTO categoryDTO) {
        log.info("Creating new category: {}", categoryDTO.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDTO));
    }

    /**
     * Get all categories
     */
    @GetMapping
    public ResponseEntity<DataResult<List<CategoryDTO>>> getAllCategories() {
        log.info("Retrieving all categories");
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/parents")
    public ResponseEntity<DataResult<List<CategoryDTO>>> getAllParentCategories() {
        log.info("Retrieving all parent categories");
        return ResponseEntity.ok(categoryService.getParentCategories());
    }

    @GetMapping("/parentsWithSubcategories")
    public ResponseEntity<DataResult<List<CategorySubcategoryDTO>>> getAllPatentCategoriesWithSubcategories() {
        log.info("Retrieving all parent categories with subcategories");
        return ResponseEntity.ok(categoryService.getParentCategoryWithSubcategories());
    }

    @GetMapping("/subcategories/{parentId}")
    public ResponseEntity<DataResult<List<CategoryDTO>>> getAllSubCategoriesByParentId(@PathVariable Long parentId) {
        log.info("Retrieving all  subcategories");
        return ResponseEntity.ok(categoryService.getSubCategoriesByParentId(parentId));
    }

    /**
     * Get category by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<DataResult<CategoryDTO>> getCategoryById(@PathVariable Long id) {
        log.info("Retrieving category by ID: {}", id);
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    /**
     * Get category by name
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<DataResult<CategoryDTO>> getCategoryByName(@PathVariable String name) {
        log.info("Retrieving category by name: {}", name);
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    /**
     * Update an existing category
     */
    @PutMapping("/{id}")
    public ResponseEntity<DataResult<CategoryDTO>> updateCategory(@PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        log.info("Updating category with ID: {}", id);
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryService.mapToEntity(categoryDTO)));
    }

    /**
     * Delete a category by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteCategory(@PathVariable Long id) {
        log.info("Deleting category with ID: {}", id);
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

    /**
     * Search categories by name
     */
    @GetMapping("/search/name")
    public ResponseEntity<DataResult<List<CategoryDTO>>> searchCategoriesByName(@RequestParam String name) {
        log.info("Searching categories by name: {}", name);
        return ResponseEntity.ok(categoryService.searchCategoriesByName(name));
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
