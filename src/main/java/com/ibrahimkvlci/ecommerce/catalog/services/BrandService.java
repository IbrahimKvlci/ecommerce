package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.BrandDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;

import java.util.List;

public interface BrandService {

    BrandDTO createBrand(Brand brand);

    List<BrandDTO> getAllBrands();

    BrandDTO getBrandById(Long id);

    BrandDTO getBrandByName(String name);

    BrandDTO updateBrand(Long id, Brand brand);

    void deleteBrand(Long id);

    List<BrandDTO> searchBrandsByName(String name);

    boolean existsByName(String name);
    
    /**
     * Convert DTO to entity
     */
    Brand mapToEntity(BrandDTO brandDTO);
    
    /**
     * Create DTO from entity
     */
    BrandDTO mapToDTO(Brand brand);
}


