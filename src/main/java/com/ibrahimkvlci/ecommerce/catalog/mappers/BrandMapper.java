package com.ibrahimkvlci.ecommerce.catalog.mappers;

import org.springframework.stereotype.Component;

import com.ibrahimkvlci.ecommerce.catalog.dto.BrandDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.Brand;

@Component
public class BrandMapper {

    public Brand toEntity(BrandDTO brandDTO) {
        if (brandDTO == null) {
            return null;
        }
        Brand brand = new Brand();
        brand.setId(brandDTO.getId());
        brand.setName(brandDTO.getName());
        return brand;
    }

    public BrandDTO toDTO(Brand brand) {
        if (brand == null) {
            return null;
        }
        BrandDTO dto = new BrandDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        return dto;
    }
}
