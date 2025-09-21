package com.ibrahimkvlci.ecommerce.address.repositories;

import com.ibrahimkvlci.ecommerce.address.models.AddressDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for AddressDetail entity operations
 */
@Repository
public interface AddressDetailRepository extends JpaRepository<AddressDetail, Long> {
    
    /**
     * Find address details by country ID
     */
    List<AddressDetail> findByCountryId(Long countryId);
    
    /**
     * Find address details by city ID
     */
    List<AddressDetail> findByCityId(Long cityId);
    
    /**
     * Find address details by district ID
     */
    List<AddressDetail> findByDistrictId(Long districtId);
    
    /**
     * Find address details by neighborhood ID
     */
    List<AddressDetail> findByNeighborhoodId(Long neighborhoodId);
    
    /**
     * Find address details by name containing (case insensitive)
     */
    List<AddressDetail> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find address details by surname containing (case insensitive)
     */
    List<AddressDetail> findBySurnameContainingIgnoreCase(String surname);
    
    /**
     * Find address details by phone containing
     */
    List<AddressDetail> findByPhoneContaining(String phone);
    
    /**
     * Find address details by address containing (case insensitive)
     */
    List<AddressDetail> findByAddressContainingIgnoreCase(String address);
    
    /**
     * Find address details by name and surname (case insensitive)
     */
    @Query("SELECT a FROM AddressDetail a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(a.surname) LIKE LOWER(CONCAT('%', :surname, '%'))")
    List<AddressDetail> findByNameAndSurnameContaining(@Param("name") String name, @Param("surname") String surname);
    
    /**
     * Find all address details with full hierarchy information
     */
    @Query("SELECT a FROM AddressDetail a JOIN FETCH a.country JOIN FETCH a.city JOIN FETCH a.district JOIN FETCH a.neighborhood ORDER BY a.name ASC")
    List<AddressDetail> findAllWithFullHierarchy();
    
    /**
     * Find address details by country and city
     */
    @Query("SELECT a FROM AddressDetail a WHERE a.country.id = :countryId AND a.city.id = :cityId")
    List<AddressDetail> findByCountryAndCity(@Param("countryId") Long countryId, @Param("cityId") Long cityId);
    
    /**
     * Find address details by country, city and district
     */
    @Query("SELECT a FROM AddressDetail a WHERE a.country.id = :countryId AND a.city.id = :cityId AND a.district.id = :districtId")
    List<AddressDetail> findByCountryCityAndDistrict(@Param("countryId") Long countryId, @Param("cityId") Long cityId, @Param("districtId") Long districtId);
}
