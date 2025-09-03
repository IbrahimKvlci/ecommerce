package com.ibrahimkvlci.ecommerce.catalog.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ibrahimkvlci.ecommerce.catalog.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.CategoryNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.CategoryValidationException;
import com.ibrahimkvlci.ecommerce.catalog.models.Category;
import com.ibrahimkvlci.ecommerce.catalog.repositories.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryServiceImpl categoryService;

	@Test
	public void testCreateCategorySuccess() {
		CategoryDTO dto = new CategoryDTO(null, "Electronics");
		when(categoryRepository.existsByNameIgnoreCase("Electronics")).thenReturn(false);
		when(categoryRepository.save(any(Category.class))).thenAnswer(inv -> {
			Category c = inv.getArgument(0);
			c.setId(1L);
			return c;
		});

		CategoryDTO created = categoryService.createCategory(dto.toEntity());
		assertEquals(1L, created.getId());
		assertEquals("Electronics", created.getName());
	}

	@Test
	public void testCreateCategoryDuplicateName() {
		CategoryDTO dto = new CategoryDTO(null, "Electronics");
		when(categoryRepository.existsByNameIgnoreCase("Electronics")).thenReturn(true);
		assertThrows(CategoryValidationException.class, () -> categoryService.createCategory(dto.toEntity()));
	}

	@Test
	public void testGetAllCategories() {
		List<Category> list = Arrays.asList(new Category(1L, "A", null), new Category(2L, "B", null));
		when(categoryRepository.findAll()).thenReturn(list);
		List<CategoryDTO> result = categoryService.getAllCategories();
		assertEquals(2, result.size());
	}

	@Test
	public void testGetCategoryByIdNotFound() {
		when(categoryRepository.findById(99L)).thenReturn(Optional.empty());
		assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById(99L));
	}

	@Test
	public void testGetCategoryByIdFound() {
		when(categoryRepository.findById(5L)).thenReturn(Optional.of(new Category(5L, "X", null)));
		CategoryDTO dto = categoryService.getCategoryById(5L);
		assertEquals(5L, dto.getId());
	}

	@Test
	public void testGetCategoryByNameNotFound() {
		when(categoryRepository.findByNameIgnoreCase("Z")).thenReturn(Optional.empty());
		assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryByName("Z"));
	}

	@Test
	public void testUpdateCategoryConflictName() {
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category(1L, "Old", null)));
		when(categoryRepository.existsByNameIgnoreCase("New")).thenReturn(true);
		assertThrows(CategoryValidationException.class, () -> categoryService.updateCategory(1L, new CategoryDTO(null, "New").toEntity()));
	}

	@Test
	public void testUpdateCategorySuccess() {
		when(categoryRepository.findById(2L)).thenReturn(Optional.of(new Category(2L, "Old", null)));
		when(categoryRepository.existsByNameIgnoreCase("New")).thenReturn(false);
		when(categoryRepository.save(any(Category.class))).thenAnswer(inv -> inv.getArgument(0));
		CategoryDTO updated = categoryService.updateCategory(2L, new CategoryDTO(null, "New").toEntity());
		assertEquals("New", updated.getName());
	}

	@Test
	public void testDeleteCategoryNotFound() {
		when(categoryRepository.existsById(3L)).thenReturn(false);
		assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory(3L));
	}

	@Test
	public void testSearchCategoriesByName() {
		when(categoryRepository.findByNameContainingIgnoreCase("nic")).thenReturn(Arrays.asList(new Category(1L, "Electronics", null)));
		List<CategoryDTO> list = categoryService.searchCategoriesByName("nic");
		assertEquals(1, list.size());
	}

	@Test
	public void testExistsByName() {
		when(categoryRepository.existsByNameIgnoreCase("Electronics")).thenReturn(true);
		assertEquals(true, categoryService.existsByName("Electronics"));
	}
}


