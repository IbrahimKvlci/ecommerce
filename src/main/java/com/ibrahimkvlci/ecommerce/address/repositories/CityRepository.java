package com.ibrahimkvlci.ecommerce.address.repositories;

import com.ibrahimkvlci.ecommerce.address.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for City entity operations
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    
    /**
     * Find cities by country ID
     */
    List<City> findByCountryId(Long countryId);
    
    /**
     * Find city by name and country ID
     */
    Optional<City> findByNameAndCountryId(String name, Long countryId);
    
    /**
     * Find city by name (case insensitive)
     */
    Optional<City> findByNameIgnoreCase(String name);
    
    /**
     * Find cities by name containing (case insensitive)
     */
    List<City> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find cities by country ID and name containing (case insensitive)
     */
    List<City> findByCountryIdAndNameContainingIgnoreCase(Long countryId, String name);
    
    /**
     * Check if city exists by name and country
     */
    boolean existsByNameAndCountryId(String name, Long countryId);
    
    /**
     * Find all cities with country information
     */
    @Query("SELECT c FROM City c JOIN FETCH c.country ORDER BY c.name ASC")
    List<City> findAllWithCountry();
    
    /**
     * Find cities by country ID ordered by name
     */
    @Query("SELECT c FROM City c WHERE c.country.id = :countryId ORDER BY c.name ASC")
    List<City> findByCountryIdOrderedByName(@Param("countryId") Long countryId);
}
