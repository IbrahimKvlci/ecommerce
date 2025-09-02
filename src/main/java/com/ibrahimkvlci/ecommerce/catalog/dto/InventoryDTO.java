package com.ibrahimkvlci.ecommerce.catalog.dto;

import com.ibrahimkvlci.ecommerce.catalog.models.Inventory;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {

    private Long id;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    public Inventory toEntity(Product product) {
        Inventory inventory = new Inventory();
        inventory.setId(this.id);
        inventory.setProduct(product);
        inventory.setQuantity(this.quantity);
        return inventory;
    }

    public static InventoryDTO fromEntity(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(inventory.getId());
        dto.setProductId(inventory.getProduct().getId());
        dto.setQuantity(inventory.getQuantity());
        return dto;
    }
}


