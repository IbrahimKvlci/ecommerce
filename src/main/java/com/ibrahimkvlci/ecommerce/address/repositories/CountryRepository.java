package com.ibrahimkvlci.ecommerce.address.repositories;

import com.ibrahimkvlci.ecommerce.address.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Country entity operations
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    
    /**
     * Find country by name (case insensitive)
     */
    Optional<Country> findByNameIgnoreCase(String name);
    
    /**
     * Find country by code (case insensitive)
     */
    Optional<Country> findByCodeIgnoreCase(String code);
    
    /**
     * Find countries by name containing (case insensitive)
     */
    List<Country> findByNameContainingIgnoreCase(String name);
    
    /**
     * Check if country exists by name
     */
    boolean existsByNameIgnoreCase(String name);
    
    /**
     * Check if country exists by code
     */
    boolean existsByCodeIgnoreCase(String code);
    
    /**
     * Find all countries ordered by name
     */
    @Query("SELECT c FROM Country c ORDER BY c.name ASC")
    List<Country> findAllOrderedByName();
}
