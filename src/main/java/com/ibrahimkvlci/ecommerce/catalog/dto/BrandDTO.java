package com.ibrahimkvlci.ecommerce.catalog.dto;

import com.ibrahimkvlci.ecommerce.catalog.models.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {

    private Long id;

    @NotBlank(message = "Brand name is required")
    @Size(min = 1, max = 100, message = "Brand name must be between 1 and 100 characters")
    private String name;

    public Brand toEntity() {
        Brand brand = new Brand();
        brand.setId(this.id);
        brand.setName(this.name);
        return brand;
    }

    public static BrandDTO fromEntity(Brand brand) {
        BrandDTO dto = new BrandDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        return dto;
    }
}


