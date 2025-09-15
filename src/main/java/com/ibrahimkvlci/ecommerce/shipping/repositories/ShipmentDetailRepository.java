package com.ibrahimkvlci.ecommerce.shipping.repositories;

import com.ibrahimkvlci.ecommerce.shipping.models.ShipmentDetail;
import com.ibrahimkvlci.ecommerce.shipping.models.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for ShipmentDetail entity operations.
 * Provides CRUD operations and custom queries for shipment management.
 */
@Repository
public interface ShipmentDetailRepository extends JpaRepository<ShipmentDetail, Long> {
    
    /**
     * Find shipment details by order ID
     */
    Optional<ShipmentDetail> findByOrderId(Long orderId);
    
    /**
     * Find all shipments for a specific customer
     */
    @Query("SELECT s FROM ShipmentDetail s JOIN s.addressDetail a WHERE a.customerId = :customerId")
    List<ShipmentDetail> findByCustomerId(@Param("customerId") Long customerId);
    
    /**
     * Find shipments by shipping method
     */
    List<ShipmentDetail> findByShippingMethod(ShippingMethod shippingMethod);
    
    /**
     * Find shipments by city
     */
    @Query("SELECT s FROM ShipmentDetail s JOIN s.addressDetail a WHERE a.city = :city")
    List<ShipmentDetail> findByCity(@Param("city") String city);
    
    /**
     * Find shipments by state
     */
    @Query("SELECT s FROM ShipmentDetail s JOIN s.addressDetail a WHERE a.state = :state")
    List<ShipmentDetail> findByState(@Param("state") String state);
    
    /**
     * Find shipments by ZIP code
     */
    @Query("SELECT s FROM ShipmentDetail s JOIN s.addressDetail a WHERE a.zipCode = :zipCode")
    List<ShipmentDetail> findByZipCode(@Param("zipCode") String zipCode);
    
    /**
     * Check if a shipment exists for an order
     */
    boolean existsByOrderId(Long orderId);
}
