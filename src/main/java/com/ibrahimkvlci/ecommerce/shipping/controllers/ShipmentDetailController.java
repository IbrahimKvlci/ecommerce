package com.ibrahimkvlci.ecommerce.shipping.controllers;

import com.ibrahimkvlci.ecommerce.shipping.dto.ShipmentDetailDTO;
import com.ibrahimkvlci.ecommerce.shipping.models.ShippingMethod;
import com.ibrahimkvlci.ecommerce.shipping.services.ShipmentDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for ShipmentDetail operations.
 * Provides endpoints for CRUD operations and shipment management.
 */
@RestController
@RequestMapping("/api/shipping/shipments")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ShipmentDetailController {
    
    private final ShipmentDetailService shipmentDetailService;
    
    /**
     * Create a new shipment detail
     */
    @PostMapping
    public ResponseEntity<ShipmentDetailDTO> createShipmentDetail(@Valid @RequestBody ShipmentDetailDTO shipmentDetailDTO) {
        ShipmentDetailDTO createdShipmentDetail = shipmentDetailService.createShipmentDetail(shipmentDetailDTO);
        return new ResponseEntity<>(createdShipmentDetail, HttpStatus.CREATED);
    }
    
    /**
     * Get all shipment details
     */
    @GetMapping
    public ResponseEntity<List<ShipmentDetailDTO>> getAllShipmentDetails() {
        List<ShipmentDetailDTO> shipmentDetails = shipmentDetailService.getAllShipmentDetails();
        return ResponseEntity.ok(shipmentDetails);
    }
    
    /**
     * Get shipment detail by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShipmentDetailDTO> getShipmentDetailById(@PathVariable Long id) {
        Optional<ShipmentDetailDTO> shipmentDetail = shipmentDetailService.getShipmentDetailById(id);
        return shipmentDetail.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get shipment detail by order ID
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ShipmentDetailDTO> getShipmentDetailByOrderId(@PathVariable Long orderId) {
        Optional<ShipmentDetailDTO> shipmentDetail = shipmentDetailService.getShipmentDetailByOrderId(orderId);
        return shipmentDetail.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Update an existing shipment detail
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShipmentDetailDTO> updateShipmentDetail(
            @PathVariable Long id, 
            @Valid @RequestBody ShipmentDetailDTO shipmentDetailDTO) {
        ShipmentDetailDTO updatedShipmentDetail = shipmentDetailService.updateShipmentDetail(id, shipmentDetailDTO);
        return ResponseEntity.ok(updatedShipmentDetail);
    }
    
    /**
     * Delete a shipment detail
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipmentDetail(@PathVariable Long id) {
        shipmentDetailService.deleteShipmentDetail(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Get all shipments for a specific customer
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ShipmentDetailDTO>> getShipmentDetailsByCustomerId(@PathVariable Long customerId) {
        List<ShipmentDetailDTO> shipmentDetails = shipmentDetailService.getShipmentDetailsByCustomerId(customerId);
        return ResponseEntity.ok(shipmentDetails);
    }
    
    /**
     * Get shipments by shipping method
     */
    @GetMapping("/method/{shippingMethod}")
    public ResponseEntity<List<ShipmentDetailDTO>> getShipmentDetailsByShippingMethod(@PathVariable ShippingMethod shippingMethod) {
        List<ShipmentDetailDTO> shipmentDetails = shipmentDetailService.getShipmentDetailsByShippingMethod(shippingMethod);
        return ResponseEntity.ok(shipmentDetails);
    }
    
    /**
     * Get shipments by city
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<List<ShipmentDetailDTO>> getShipmentDetailsByCity(@PathVariable String city) {
        List<ShipmentDetailDTO> shipmentDetails = shipmentDetailService.getShipmentDetailsByCity(city);
        return ResponseEntity.ok(shipmentDetails);
    }
    
    /**
     * Get shipments by state
     */
    @GetMapping("/state/{state}")
    public ResponseEntity<List<ShipmentDetailDTO>> getShipmentDetailsByState(@PathVariable String state) {
        List<ShipmentDetailDTO> shipmentDetails = shipmentDetailService.getShipmentDetailsByState(state);
        return ResponseEntity.ok(shipmentDetails);
    }
    
    /**
     * Get shipments by ZIP code
     */
    @GetMapping("/zip/{zipCode}")
    public ResponseEntity<List<ShipmentDetailDTO>> getShipmentDetailsByZipCode(@PathVariable String zipCode) {
        List<ShipmentDetailDTO> shipmentDetails = shipmentDetailService.getShipmentDetailsByZipCode(zipCode);
        return ResponseEntity.ok(shipmentDetails);
    }
}
