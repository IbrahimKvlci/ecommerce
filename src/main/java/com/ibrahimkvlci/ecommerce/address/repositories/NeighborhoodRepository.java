package com.ibrahimkvlci.ecommerce.address.repositories;

import com.ibrahimkvlci.ecommerce.address.models.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Neighborhood entity operations
 */
@Repository
public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Long> {
    
    /**
     * Find neighborhoods by district ID
     */
    List<Neighborhood> findByDistrictId(Long districtId);
    
    /**
     * Find neighborhood by name and district ID
     */
    Optional<Neighborhood> findByNameAndDistrictId(String name, Long districtId);
    
    /**
     * Find neighborhood by name (case insensitive)
     */
    Optional<Neighborhood> findByNameIgnoreCase(String name);
    
    /**
     * Find neighborhoods by name containing (case insensitive)
     */
    List<Neighborhood> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find neighborhoods by district ID and name containing (case insensitive)
     */
    List<Neighborhood> findByDistrictIdAndNameContainingIgnoreCase(Long districtId, String name);
    
    /**
     * Check if neighborhood exists by name and district
     */
    boolean existsByNameAndDistrictId(String name, Long districtId);
    
    /**
     * Find all neighborhoods with full hierarchy information
     */
    @Query("SELECT n FROM Neighborhood n JOIN FETCH n.district d JOIN FETCH d.city c JOIN FETCH c.country ORDER BY n.name ASC")
    List<Neighborhood> findAllWithFullHierarchy();
    
    /**
     * Find neighborhoods by district ID ordered by name
     */
    @Query("SELECT n FROM Neighborhood n WHERE n.district.id = :districtId ORDER BY n.name ASC")
    List<Neighborhood> findByDistrictIdOrderedByName(@Param("districtId") Long districtId);
}
