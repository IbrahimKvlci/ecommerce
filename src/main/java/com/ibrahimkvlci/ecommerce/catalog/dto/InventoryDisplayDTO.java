package com.ibrahimkvlci.ecommerce.catalog.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDisplayDTO {

    private InventoryDTO inventoryDTO;
    private List<OtherInventoryDTO> otherInventories;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OtherInventoryDTO {
        private Long id;
        private Long sellerId;
        private Double price;
        private Integer quantity;
    }

}
