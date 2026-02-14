package com.ibrahimkvlci.ecommerce.catalog.models.id;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryId implements Serializable {
    private Long product;
    private Long sellerId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InventoryId that = (InventoryId) o;
        return Objects.equals(sellerId, that.sellerId) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerId, product);
    }
}
