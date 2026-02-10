package com.ibrahimkvlci.ecommerce.catalog.models;

import java.util.ArrayList;
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

    private Long brandId;

    private String brand;

    private Long categoryId;

    private String category;

    private Boolean featured;

    private List<String> images = new ArrayList<>();

    private List<AttributeItem> attributes = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttributeItem {
        private String key;

        private List<String> value;

    }
}
