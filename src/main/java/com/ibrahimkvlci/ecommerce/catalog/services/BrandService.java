package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.BrandDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;

import java.util.List;

public interface BrandService {

    DataResult<BrandDTO> createBrand(Brand brand);

    DataResult<List<BrandDTO>> getAllBrands();

    DataResult<BrandDTO> getBrandById(Long id);

    DataResult<BrandDTO> getBrandByName(String name);

    DataResult<BrandDTO> updateBrand(Long id, Brand brand);

    Result deleteBrand(Long id);

    DataResult<List<BrandDTO>> searchBrandsByName(String name);

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
