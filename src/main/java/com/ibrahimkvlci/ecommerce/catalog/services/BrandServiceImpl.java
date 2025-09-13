package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.BrandDTO;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.BrandNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.BrandValidationException;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.repositories.BrandRepository;
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
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public BrandDTO createBrand(Brand brand) {
        log.info("Creating new brand: {}", brand.getName());

        if (brandRepository.existsByNameIgnoreCase(brand.getName())) {
            throw new BrandValidationException("Brand with name '" + brand.getName() + "' already exists");
        }

        Brand saved = brandRepository.save(brand);
        return this.mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BrandDTO getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found with ID: " + id));
        return this.mapToDTO(brand);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandDTO getBrandByName(String name) {
        Brand brand = brandRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found with name: " + name));
        return this.mapToDTO(brand);
    }

    @Override
    public BrandDTO updateBrand(Long id, Brand brand) {
        Brand existing = brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found with ID: " + id));

        if (!existing.getName().equalsIgnoreCase(brand.getName())
                && brandRepository.existsByNameIgnoreCase(brand.getName())) {
            throw new BrandValidationException("Brand with name '" + brand.getName() + "' already exists");
        }

        existing.setName(brand.getName());
        Brand updated = brandRepository.save(existing);
        return this.mapToDTO(updated);
    }

    @Override
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new BrandNotFoundException("Brand not found with ID: " + id);
        }
        brandRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandDTO> searchBrandsByName(String name) {
        return brandRepository.findByNameContainingIgnoreCase(name).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return brandRepository.existsByNameIgnoreCase(name);
    }
    
    /**
     * Convert DTO to entity
     */
    public Brand mapToEntity(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setId(brandDTO.getId());
        brand.setName(brandDTO.getName());
        return brand;
    }
    
    /**
     * Create DTO from entity
     */
    public BrandDTO mapToDTO(Brand brand) {
        BrandDTO dto = new BrandDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        return dto;
    }
}


