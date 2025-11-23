package com.ibrahimkvlci.ecommerce.address.repositories;

import com.ibrahimkvlci.ecommerce.address.models.AddressDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for AddressDetail entity operations
 */
@Repository
public interface AddressDetailRepository extends JpaRepository<AddressDetail, Long> {

    List<AddressDetail> findAllByCustomerIdOrderByIdAsc(Long customerId);

    /**
     * Find all address details with full hierarchy information
     */
    @Query("SELECT a FROM AddressDetail a JOIN FETCH a.country JOIN FETCH a.city JOIN FETCH a.district JOIN FETCH a.neighborhood ORDER BY a.name ASC")
    List<AddressDetail> findAllWithFullHierarchy();

    @Modifying
    @Query("UPDATE AddressDetail a SET a.isDefaultAddress = false WHERE a.customerId = :customerId AND a.id != :addressDetailId")
    void unsetDefaultAddressExceptThis(@Param("customerId") Long customerId,
            @Param("addressDetailId") Long addressDetailId);
}
