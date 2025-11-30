package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.BrandDTO;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.BrandNotFoundException;
import com.ibrahimkvlci.ecommerce.catalog.exceptions.BrandValidationException;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.repositories.BrandRepository;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public DataResult<BrandDTO> createBrand(Brand brand) {
        log.info("Creating new brand: {}", brand.getName());

        if (brandRepository.existsByNameIgnoreCase(brand.getName())) {
            throw new BrandValidationException("Brand with name '" + brand.getName() + "' already exists");
        }

        Brand saved = brandRepository.save(brand);
        return new SuccessDataResult<>("Brand created successfully", this.mapToDTO(saved));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<BrandDTO>> getAllBrands() {
        return new SuccessDataResult<>("Brands listed successfully",
                brandRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<BrandDTO> getBrandById(Long id) {
        Brand brand = brandRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new BrandNotFoundException("Brand not found with ID: " + id));
        return new SuccessDataResult<>("Brand found successfully", this.mapToDTO(brand));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<BrandDTO> getBrandByName(String name) {
        Brand brand = brandRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found with name: " + name));
        return new SuccessDataResult<>("Brand found successfully", this.mapToDTO(brand));
    }

    @Override
    public DataResult<BrandDTO> updateBrand(Long id, Brand brand) {
        Brand existing = brandRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new BrandNotFoundException("Brand not found with ID: " + id));

        if (!existing.getName().equalsIgnoreCase(brand.getName())
                && brandRepository.existsByNameIgnoreCase(brand.getName())) {
            throw new BrandValidationException("Brand with name '" + brand.getName() + "' already exists");
        }

        existing.setName(brand.getName());
        Brand updated = brandRepository.save(existing);
        return new SuccessDataResult<>("Brand updated successfully", this.mapToDTO(updated));
    }

    @Override
    public Result deleteBrand(Long id) {
        if (!brandRepository.existsById(Objects.requireNonNull(id))) {
            throw new BrandNotFoundException("Brand not found with ID: " + id);
        }
        brandRepository.deleteById(Objects.requireNonNull(id));
        return new SuccessResult("Brand deleted successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<BrandDTO>> searchBrandsByName(String name) {
        return new SuccessDataResult<>("Brands found successfully",
                brandRepository.findByNameContainingIgnoreCase(name).stream().map(this::mapToDTO)
                        .collect(Collectors.toList()));
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
