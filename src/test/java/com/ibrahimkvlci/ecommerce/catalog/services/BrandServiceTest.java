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

import com.ibrahimkvlci.ecommerce.catalog.dto.BrandDTO;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.BrandNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.BrandValidationException;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.repositories.BrandRepository;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

	@Mock
	private BrandRepository brandRepository;

	@InjectMocks
	private BrandServiceImpl brandService;

	@Test
	public void testCreateBrandSuccess() {
		BrandDTO dto = new BrandDTO(null, "Acme");
		when(brandRepository.existsByNameIgnoreCase("Acme")).thenReturn(false);
		when(brandRepository.save(any(Brand.class))).thenAnswer(inv -> {
			Brand b = inv.getArgument(0);
			b.setId(1L);
			return b;
		});

		BrandDTO created = brandService.createBrand(brandService.mapToEntity(dto));
		assertEquals(1L, created.getId());
		assertEquals("Acme", created.getName());
	}

	@Test
	public void testCreateBrandDuplicateName() {
		BrandDTO dto = new BrandDTO(null, "Acme");
		when(brandRepository.existsByNameIgnoreCase("Acme")).thenReturn(true);
		assertThrows(BrandValidationException.class, () -> brandService.createBrand(brandService.mapToEntity(dto)));
	}

	@Test
	public void testGetAllBrands() {
		List<Brand> list = Arrays.asList(new Brand(1L, "A", null), new Brand(2L, "B", null));
		when(brandRepository.findAll()).thenReturn(list);
		List<BrandDTO> result = brandService.getAllBrands();
		assertEquals(2, result.size());
	}

	@Test
	public void testGetBrandByIdNotFound() {
		when(brandRepository.findById(99L)).thenReturn(Optional.empty());
		assertThrows(BrandNotFoundException.class, () -> brandService.getBrandById(99L));
	}

	@Test
	public void testGetBrandByIdFound() {
		when(brandRepository.findById(5L)).thenReturn(Optional.of(new Brand(5L, "X", null)));
		BrandDTO dto = brandService.getBrandById(5L);
		assertEquals(5L, dto.getId());
	}

	@Test
	public void testGetBrandByNameNotFound() {
		when(brandRepository.findByNameIgnoreCase("Z")).thenReturn(Optional.empty());
		assertThrows(BrandNotFoundException.class, () -> brandService.getBrandByName("Z"));
	}

	@Test
	public void testUpdateBrandConflictName() {
		when(brandRepository.findById(1L)).thenReturn(Optional.of(new Brand(1L, "Old", null)));
		when(brandRepository.existsByNameIgnoreCase("New")).thenReturn(true);
		assertThrows(BrandValidationException.class, () -> brandService.updateBrand(1L, brandService.mapToEntity(new BrandDTO(null, "New"))));
	}

	@Test
	public void testUpdateBrandSuccess() {
		when(brandRepository.findById(2L)).thenReturn(Optional.of(new Brand(2L, "Old", null)));
		when(brandRepository.existsByNameIgnoreCase("New")).thenReturn(false);
		when(brandRepository.save(any(Brand.class))).thenAnswer(inv -> inv.getArgument(0));
		BrandDTO updated = brandService.updateBrand(2L, brandService.mapToEntity(new BrandDTO(null, "New")));
		assertEquals("New", updated.getName());
	}

	@Test
	public void testDeleteBrandNotFound() {
		when(brandRepository.existsById(3L)).thenReturn(false);
		assertThrows(BrandNotFoundException.class, () -> brandService.deleteBrand(3L));
	}

	@Test
	public void testSearchBrandsByName() {
		when(brandRepository.findByNameContainingIgnoreCase("me")).thenReturn(Arrays.asList(new Brand(1L, "Acme", null)));
		List<BrandDTO> list = brandService.searchBrandsByName("me");
		assertEquals(1, list.size());
	}

	@Test
	public void testExistsByName() {
		when(brandRepository.existsByNameIgnoreCase("Acme")).thenReturn(true);
		assertEquals(true, brandService.existsByName("Acme"));
	}
}


