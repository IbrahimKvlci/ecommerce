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

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.services.InventoryService;

@ExtendWith(MockitoExtension.class)
public class InventoryControllerTest {

	@Mock
	private InventoryService inventoryService;

	@InjectMocks
	private InventoryController inventoryController;

	@Test
	public void testCreateInventory() {
		InventoryDTO dto = new InventoryDTO(1L,null, 5, 1L);
		InventoryDTO saved = new InventoryDTO(10L, 1L, 5, 1L);
		Product product = new Product();
		product.setId(1L);
		when(inventoryService.createInventory(inventoryService.mapToEntity(dto))).thenReturn(saved);

		ResponseEntity<InventoryDTO> response = inventoryController.createInventory(dto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		InventoryDTO body = response.getBody();
		assertNotNull(body);
		assertEquals(10L, body.getId());
		assertEquals(1L, body.getProductId());
		assertEquals(5, body.getQuantity());
	}

	@Test
	public void testGetAllInventories() {
		List<InventoryDTO> list = Arrays.asList(new InventoryDTO(1L,1L, 2, 3l));
		when(inventoryService.getAllInventories()).thenReturn(list);

		ResponseEntity<List<InventoryDTO>> response = inventoryController.getAllInventories();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		List<InventoryDTO> body = response.getBody();
		assertNotNull(body);
		assertEquals(1, body.size());
	}

	@Test
	public void testGetInventoryById() {
		InventoryDTO dto = new InventoryDTO(3L, 4L, 7, 3L);
		when(inventoryService.getInventoryById(3L)).thenReturn(dto);

		ResponseEntity<InventoryDTO> response = inventoryController.getInventoryById(3L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		InventoryDTO body = response.getBody();
		assertNotNull(body);
		assertEquals(3L, body.getId());
	}

	@Test
	public void testGetInventoriesByProductId() {
		List<InventoryDTO> list = Arrays.asList(new InventoryDTO(5L, 9L, 1, 5L));
		when(inventoryService.getInventoriesByProductId(9L)).thenReturn(list);

		ResponseEntity<List<InventoryDTO>> response = inventoryController.getInventoriesByProductId(9L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		List<InventoryDTO> body = response.getBody();
		assertNotNull(body);
		assertEquals(1, body.size());
	}

	@Test
	public void testUpdateInventory() {
		InventoryDTO update = new InventoryDTO(null, 1L, 20, 1L);
		InventoryDTO updated = new InventoryDTO(7L, 1L, 20, 1L);
		Product product = new Product();
		product.setId(1L);
		when(inventoryService.updateInventory(7L, inventoryService.mapToEntity(update))).thenReturn(updated);

		ResponseEntity<InventoryDTO> response = inventoryController.updateInventory(7L, update);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		InventoryDTO body = response.getBody();
		assertNotNull(body);
		assertEquals(20, body.getQuantity());
	}

	@Test
	public void testDeleteInventory() {
		ResponseEntity<Void> response = inventoryController.deleteInventory(8L);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
}


