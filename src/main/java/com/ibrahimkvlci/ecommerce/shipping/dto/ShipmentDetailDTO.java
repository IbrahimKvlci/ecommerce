package com.ibrahimkvlci.ecommerce.shipping.dto;

import com.ibrahimkvlci.ecommerce.shipping.models.ShippingMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for ShipmentDetail operations.
 * Used for API requests and responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDetailDTO {
    
    private Long id;
    
    @NotNull(message = "Order ID is required")
    private Long orderId;
    
    private Long addressDetailId;
    
    @NotNull(message = "Shipping method is required")
    private ShippingMethod shippingMethod;
    

}
