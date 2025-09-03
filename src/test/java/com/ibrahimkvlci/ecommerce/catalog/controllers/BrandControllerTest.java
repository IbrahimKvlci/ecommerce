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

import com.ibrahimkvlci.ecommerce.catalog.dto.BrandDTO;
import com.ibrahimkvlci.ecommerce.catalog.services.BrandService;

@ExtendWith(MockitoExtension.class)
public class BrandControllerTest {

	@Mock
	private BrandService brandService;

	@InjectMocks
	private BrandController brandController;

	@Test
	public void testCreateBrand() {
		BrandDTO dto = new BrandDTO(null, "Acme");
		BrandDTO saved = new BrandDTO(1L, "Acme");
		when(brandService.createBrand(dto.toEntity())).thenReturn(saved);

		ResponseEntity<BrandDTO> response = brandController.createBrand(dto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		BrandDTO body = response.getBody();
		assertNotNull(body);
		assertEquals(1L, body.getId());
		assertEquals("Acme", body.getName());
	}

	@Test
	public void testGetAllBrands() {
		List<BrandDTO> list = Arrays.asList(new BrandDTO(1L, "A"), new BrandDTO(2L, "B"));
		when(brandService.getAllBrands()).thenReturn(list);

		ResponseEntity<List<BrandDTO>> response = brandController.getAllBrands();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		List<BrandDTO> body = response.getBody();
		assertNotNull(body);
		assertEquals(2, body.size());
	}

	@Test
	public void testGetBrandById() {
		BrandDTO dto = new BrandDTO(5L, "X");
		when(brandService.getBrandById(5L)).thenReturn(dto);

		ResponseEntity<BrandDTO> response = brandController.getBrandById(5L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		BrandDTO body = response.getBody();
		assertNotNull(body);
		assertEquals(5L, body.getId());
	}

	@Test
	public void testGetBrandByName() {
		BrandDTO dto = new BrandDTO(10L, "Z");
		when(brandService.getBrandByName("Z")).thenReturn(dto);

		ResponseEntity<BrandDTO> response = brandController.getBrandByName("Z");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		BrandDTO body = response.getBody();
		assertNotNull(body);
		assertEquals("Z", body.getName());
	}

	@Test
	public void testUpdateBrand() {
		BrandDTO update = new BrandDTO(null, "New");
		BrandDTO updated = new BrandDTO(2L, "New");
		when(brandService.updateBrand(2L, update.toEntity())).thenReturn(updated);

		ResponseEntity<BrandDTO> response = brandController.updateBrand(2L, update);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		BrandDTO body = response.getBody();
		assertNotNull(body);
		assertEquals("New", body.getName());
	}

	@Test
	public void testDeleteBrand() {
		ResponseEntity<Void> response = brandController.deleteBrand(3L);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	public void testSearchBrandsByName() {
		List<BrandDTO> list = Arrays.asList(new BrandDTO(1L, "Acme"));
		when(brandService.searchBrandsByName("me")).thenReturn(list);

		ResponseEntity<List<BrandDTO>> response = brandController.searchBrandsByName("me");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		List<BrandDTO> body = response.getBody();
		assertNotNull(body);
		assertEquals(1, body.size());
	}

	@Test
	public void testExistsByName() {
		when(brandService.existsByName("Acme")).thenReturn(true);
		ResponseEntity<Boolean> response = brandController.existsByName("Acme");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Boolean body = response.getBody();
		assertNotNull(body);
		assertEquals(true, body);
	}
}


