package com.ibrahimkvlci.ecommerce.shipping.services;

import com.ibrahimkvlci.ecommerce.shipping.dto.ShipmentDetailDTO;
import com.ibrahimkvlci.ecommerce.shipping.models.ShipmentDetail;
import com.ibrahimkvlci.ecommerce.shipping.models.ShippingMethod;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for ShipmentDetail operations.
 * Provides business logic for shipment management.
 */
public interface ShipmentDetailService {
    
    /**
     * Create a new shipment detail
     */
    ShipmentDetailDTO createShipmentDetail(ShipmentDetailDTO shipmentDetailDTO);
    
    /**
     * Get all shipment details
     */
    List<ShipmentDetailDTO> getAllShipmentDetails();
    
    /**
     * Get shipment detail by ID
     */
    Optional<ShipmentDetailDTO> getShipmentDetailById(Long id);
    
    /**
     * Get shipment detail by order ID
     */
    Optional<ShipmentDetailDTO> getShipmentDetailByOrderId(Long orderId);
    
    /**
     * Update an existing shipment detail
     */
    ShipmentDetailDTO updateShipmentDetail(Long id, ShipmentDetailDTO shipmentDetailDTO);
    
    /**
     * Delete a shipment detail
     */
    void deleteShipmentDetail(Long id);
    
    /**
     * Get all shipments for a specific customer
     */
    List<ShipmentDetailDTO> getShipmentDetailsByCustomerId(Long customerId);
    
    /**
     * Get shipments by shipping method
     */
    List<ShipmentDetailDTO> getShipmentDetailsByShippingMethod(ShippingMethod shippingMethod);
    
    /**
     * Get shipments by city
     */
    List<ShipmentDetailDTO> getShipmentDetailsByCity(String city);
    
    /**
     * Get shipments by state
     */
    List<ShipmentDetailDTO> getShipmentDetailsByState(String state);
    
    /**
     * Get shipments by ZIP code
     */
    List<ShipmentDetailDTO> getShipmentDetailsByZipCode(String zipCode);
    
    /**
     * Check if shipment exists for order
     */
    boolean shipmentExistsForOrder(Long orderId);
    
    /**
     * Map shipment detail to DTO
     */
    ShipmentDetailDTO mapToDTO(ShipmentDetail shipmentDetail);
    
    /**
     * Map shipment detail DTO to entity
     */
    ShipmentDetail mapToEntity(ShipmentDetailDTO shipmentDetailDTO);
}
