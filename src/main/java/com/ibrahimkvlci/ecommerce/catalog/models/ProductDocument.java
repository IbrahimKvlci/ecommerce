package com.ibrahimkvlci.ecommerce.catalog.models;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDocument {

    @Id
    private Long id;

    private String title;

    private String description;

    private Double price;

    private Double discountPrice;

    private String brand;

    private String category;

    private Boolean featured;

    private List<AttributeItem> attributes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttributeItem {
        private String key;

        private String value;

    }
}
