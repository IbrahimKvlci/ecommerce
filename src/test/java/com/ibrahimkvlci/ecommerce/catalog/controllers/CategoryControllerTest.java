package com.ibrahimkvlci.ecommerce.catalog.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ibrahimkvlci.ecommerce.catalog.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.services.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

	@Mock
	private CategoryService categoryService;

	@InjectMocks
	private CategoryController categoryController;

	@Test
	public void testCreateCategory() {
		CategoryDTO dto = new CategoryDTO(null, "Electronics");
		CategoryDTO saved = new CategoryDTO(1L, "Electronics");
		when(categoryService.createCategory(dto.toEntity())).thenReturn(saved);

		ResponseEntity<CategoryDTO> response = categoryController.createCategory(dto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		CategoryDTO body = response.getBody();
		assertNotNull(body);
		assertEquals(1L, body.getId());
		assertEquals("Electronics", body.getName());
	}

	@Test
	public void testGetAllCategories() {
		List<CategoryDTO> list = Arrays.asList(new CategoryDTO(1L, "A"), new CategoryDTO(2L, "B"));
		when(categoryService.getAllCategories()).thenReturn(list);

		ResponseEntity<List<CategoryDTO>> response = categoryController.getAllCategories();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		List<CategoryDTO> body = response.getBody();
		assertNotNull(body);
		assertEquals(2, body.size());
	}

	@Test
	public void testGetCategoryById() {
		CategoryDTO dto = new CategoryDTO(5L, "X");
		when(categoryService.getCategoryById(5L)).thenReturn(dto);

		ResponseEntity<CategoryDTO> response = categoryController.getCategoryById(5L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CategoryDTO body = response.getBody();
		assertNotNull(body);
		assertEquals(5L, body.getId());
	}

	@Test
	public void testGetCategoryByName() {
		CategoryDTO dto = new CategoryDTO(10L, "Z");
		when(categoryService.getCategoryByName("Z")).thenReturn(dto);

		ResponseEntity<CategoryDTO> response = categoryController.getCategoryByName("Z");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CategoryDTO body = response.getBody();
		assertNotNull(body);
		assertEquals("Z", body.getName());
	}

	@Test
	public void testUpdateCategory() {
		CategoryDTO update = new CategoryDTO(null, "New");
		CategoryDTO updated = new CategoryDTO(2L, "New");
		when(categoryService.updateCategory(2L, update.toEntity())).thenReturn(updated);

		ResponseEntity<CategoryDTO> response = categoryController.updateCategory(2L, update);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CategoryDTO body = response.getBody();
		assertNotNull(body);
		assertEquals("New", body.getName());
	}

	@Test
	public void testDeleteCategory() {
		ResponseEntity<Void> response = categoryController.deleteCategory(3L);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	public void testSearchCategoriesByName() {
		List<CategoryDTO> list = Arrays.asList(new CategoryDTO(1L, "Electronics"));
		when(categoryService.searchCategoriesByName("nic")).thenReturn(list);

		ResponseEntity<List<CategoryDTO>> response = categoryController.searchCategoriesByName("nic");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		List<CategoryDTO> body = response.getBody();
		assertNotNull(body);
		assertEquals(1, body.size());
	}

	@Test
	public void testExistsByName() {
		when(categoryService.existsByName("Electronics")).thenReturn(true);
		ResponseEntity<Boolean> response = categoryController.existsByName("Electronics");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Boolean body = response.getBody();
		assertNotNull(body);
		assertEquals(true, body);
	}
}


