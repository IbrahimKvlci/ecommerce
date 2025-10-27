package com.ibrahimkvlci.ecommerce.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {

    private Long id;
    private ProductDTO productDTO;
    private Integer quantity;
    private Long sellerId;
    private Double price;
}
