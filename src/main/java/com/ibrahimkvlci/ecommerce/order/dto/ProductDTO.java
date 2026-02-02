package com.ibrahimkvlci.ecommerce.order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String title;
    private String description;
    private List<String> imagesUrl;
}
