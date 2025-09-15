package com.ibrahimkvlci.ecommerce.shipping.repositories;

import com.ibrahimkvlci.ecommerce.shipping.models.AddressDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for AddressDetail entity operations.
 * Provides CRUD operations and custom queries for address management.
 */
@Repository
public interface AddressDetailRepository extends JpaRepository<AddressDetail, Long> {
    
    /**
     * Find all addresses for a specific customer
     */
    List<AddressDetail> findByCustomerId(Long customerId);
    
    /**
     * Find addresses by city
     */
    List<AddressDetail> findByCity(String city);
    
    /**
     * Find addresses by state
     */
    List<AddressDetail> findByState(String state);
    
    /**
     * Find addresses by ZIP code
     */
    List<AddressDetail> findByZipCode(String zipCode);
    
    /**
     * Find addresses by city and state
     */
    List<AddressDetail> findByCityAndState(String city, String state);
    
    /**
     * Find the default address for a customer (if any)
     */
    @Query("SELECT a FROM AddressDetail a WHERE a.customerId = :customerId ORDER BY a.id ASC")
    Optional<AddressDetail> findDefaultByCustomerId(@Param("customerId") Long customerId);
    
    /**
     * Check if an address exists for a customer
     */
    boolean existsByCustomerIdAndAddressAndCityAndStateAndZipCode(
            Long customerId, String address, String city, String state, String zipCode);
}
