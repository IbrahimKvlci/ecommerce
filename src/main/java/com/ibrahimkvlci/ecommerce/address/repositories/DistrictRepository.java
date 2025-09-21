package com.ibrahimkvlci.ecommerce.address.repositories;

import com.ibrahimkvlci.ecommerce.address.models.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for District entity operations
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    
    /**
     * Find districts by city ID
     */
    List<District> findByCityId(Long cityId);
    
    /**
     * Find district by name and city ID
     */
    Optional<District> findByNameAndCityId(String name, Long cityId);
    
    /**
     * Find district by name (case insensitive)
     */
    Optional<District> findByNameIgnoreCase(String name);
    
    /**
     * Find districts by name containing (case insensitive)
     */
    List<District> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find districts by city ID and name containing (case insensitive)
     */
    List<District> findByCityIdAndNameContainingIgnoreCase(Long cityId, String name);
    
    /**
     * Check if district exists by name and city
     */
    boolean existsByNameAndCityId(String name, Long cityId);
    
    /**
     * Find all districts with city and country information
     */
    @Query("SELECT d FROM District d JOIN FETCH d.city c JOIN FETCH c.country ORDER BY d.name ASC")
    List<District> findAllWithCityAndCountry();
    
    /**
     * Find districts by city ID ordered by name
     */
    @Query("SELECT d FROM District d WHERE d.city.id = :cityId ORDER BY d.name ASC")
    List<District> findByCityIdOrderedByName(@Param("cityId") Long cityId);
}
