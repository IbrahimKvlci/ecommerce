package com.ibrahimkvlci.ecommerce.address.repositories;

import com.ibrahimkvlci.ecommerce.address.models.AddressDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for AddressDetail entity operations
 */
@Repository
public interface AddressDetailRepository extends JpaRepository<AddressDetail, Long> {
    
    Optional<AddressDetail> findByCustomerId(Long id);

    /**
     * Find all address details with full hierarchy information
     */
    @Query("SELECT a FROM AddressDetail a JOIN FETCH a.country JOIN FETCH a.city JOIN FETCH a.district JOIN FETCH a.neighborhood ORDER BY a.name ASC")
    List<AddressDetail> findAllWithFullHierarchy();
    
   
}
