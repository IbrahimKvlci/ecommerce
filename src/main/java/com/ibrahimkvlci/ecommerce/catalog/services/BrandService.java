package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.BrandDTO;

import java.util.List;

public interface BrandService {

    BrandDTO createBrand(BrandDTO brandDTO);

    List<BrandDTO> getAllBrands();

    BrandDTO getBrandById(Long id);

    BrandDTO getBrandByName(String name);

    BrandDTO updateBrand(Long id, BrandDTO brandDTO);

    void deleteBrand(Long id);

    List<BrandDTO> searchBrandsByName(String name);

    boolean existsByName(String name);
}


