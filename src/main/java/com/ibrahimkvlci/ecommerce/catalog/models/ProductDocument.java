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

    private Brand brand;

    private Category category;

    private Boolean featured;

    private List<String> images = new ArrayList<>();

    private List<Inventory> inventories = new ArrayList<>();

    private List<AttributeItem> attributes = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttributeItem {
        private String key;

        private List<String> value;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Inventory {
        private Long sellerId;
        private Integer stock;
        private Double price;
        private Double discountPrice;

        public Inventory(Long sellerId, Integer stock, Double price) {
            this.sellerId = sellerId;
            this.stock = stock;
            this.price = price;
            this.discountPrice = null;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Brand {
        private Long id;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {
        private Long id;
        private String name;
    }
}
