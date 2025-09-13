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

import com.ibrahimkvlci.ecommerce.catalog.dto.InventoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.InventoryNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.InventoryValidationException;
import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.repositories.InventoryRepository;
import com.ibrahimkvlci.ecommerce.catalog.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

	@Mock
	private InventoryRepository inventoryRepository;

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private InventoryServiceImpl inventoryService;

	@Test
	public void testCreateInventorySuccess() {
		InventoryDTO dto = new InventoryDTO(null, 1L, 10);
		Product product = new Product();
		product.setId(1L);
		when(productRepository.findById(1L)).thenReturn(Optional.of(product));
		when(inventoryRepository.save(any(Inventory.class))).thenAnswer(inv -> {
			Inventory i = inv.getArgument(0);
			i.setId(5L);
			return i;
		});

		InventoryDTO created = inventoryService.createInventory(inventoryService.mapToEntity(dto));
		assertEquals(5L, created.getId());
		assertEquals(1L, created.getProductId());
		assertEquals(10, created.getQuantity());
	}

	@Test
	public void testCreateInventoryProductNotFound() {
		InventoryDTO dto = new InventoryDTO(null, 99L, 10);
		Product product = new Product();
		product.setId(99L);
		when(productRepository.findById(99L)).thenReturn(Optional.empty());
		assertThrows(InventoryValidationException.class, () -> inventoryService.createInventory(inventoryService.mapToEntity(dto)));
	}

	@Test
	public void testGetAllInventories() {
		Product p = new Product();
		p.setId(2L);
		List<Inventory> list = Arrays.asList(new Inventory(1L, p, 3));
		when(inventoryRepository.findAll()).thenReturn(list);
		List<InventoryDTO> result = inventoryService.getAllInventories();
		assertEquals(1, result.size());
	}

	@Test
	public void testGetInventoryByIdNotFound() {
		when(inventoryRepository.findById(99L)).thenReturn(Optional.empty());
		assertThrows(InventoryNotFoundException.class, () -> inventoryService.getInventoryById(99L));
	}

	@Test
	public void testGetInventoriesByProductIdEmpty() {
		when(inventoryRepository.findAllByProductId(6L)).thenReturn(Arrays.asList());
		assertThrows(InventoryNotFoundException.class, () -> inventoryService.getInventoriesByProductId(6L));
	}

	@Test
	public void testUpdateInventoryMismatchProductId() {
		Product p = new Product();
		p.setId(1L);
		Inventory existing = new Inventory(7L, p, 10);
		when(inventoryRepository.findById(7L)).thenReturn(Optional.of(existing));
		InventoryDTO update = new InventoryDTO(null, 2L, 20);
		Product product = new Product();
		product.setId(2L);
		assertThrows(InventoryValidationException.class, () -> inventoryService.updateInventory(7L, inventoryService.mapToEntity(update)));
	}

	@Test
	public void testUpdateInventorySuccess() {
		Product p = new Product();
		p.setId(1L);
		Inventory existing = new Inventory(7L, p, 10);
		when(inventoryRepository.findById(7L)).thenReturn(Optional.of(existing));
		when(inventoryRepository.save(any(Inventory.class))).thenAnswer(inv -> inv.getArgument(0));
		InventoryDTO update = new InventoryDTO(null, 1L, 25);
		InventoryDTO updated = inventoryService.updateInventory(7L, inventoryService.mapToEntity(update));
		assertEquals(25, updated.getQuantity());
	}

	@Test
	public void testDeleteInventoryNotFound() {
		when(inventoryRepository.existsById(3L)).thenReturn(false);
		assertThrows(InventoryNotFoundException.class, () -> inventoryService.deleteInventory(3L));
	}
}


