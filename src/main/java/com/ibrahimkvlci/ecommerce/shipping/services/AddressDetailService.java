package com.ibrahimkvlci.ecommerce.shipping.services;

import com.ibrahimkvlci.ecommerce.shipping.dto.AddressDetailDTO;
import com.ibrahimkvlci.ecommerce.shipping.models.AddressDetail;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for AddressDetail operations.
 * Provides business logic for address management.
 */
public interface AddressDetailService {
    
    /**
     * Create a new address detail
     */
    AddressDetailDTO createAddressDetail(AddressDetailDTO addressDetailDTO);
    
    /**
     * Get all address details
     */
    List<AddressDetailDTO> getAllAddressDetails();
    
    /**
     * Get address detail by ID
     */
    Optional<AddressDetailDTO> getAddressDetailById(Long id);
    
    /**
     * Update an existing address detail
     */
    AddressDetailDTO updateAddressDetail(Long id, AddressDetailDTO addressDetailDTO);
    
    /**
     * Delete an address detail
     */
    void deleteAddressDetail(Long id);
    
    /**
     * Get all addresses for a specific customer
     */
    List<AddressDetailDTO> getAddressDetailsByCustomerId(Long customerId);
    
    /**
     * Get addresses by city
     */
    List<AddressDetailDTO> getAddressDetailsByCity(String city);
    
    /**
     * Get addresses by state
     */
    List<AddressDetailDTO> getAddressDetailsByState(String state);
    
    /**
     * Get addresses by ZIP code
     */
    List<AddressDetailDTO> getAddressDetailsByZipCode(String zipCode);
    
    /**
     * Get addresses by city and state
     */
    List<AddressDetailDTO> getAddressDetailsByCityAndState(String city, String state);
    
    /**
     * Get default address for a customer
     */
    Optional<AddressDetailDTO> getDefaultAddressByCustomerId(Long customerId);
    
    /**
     * Check if address exists for customer
     */
    boolean addressExistsForCustomer(Long customerId, String address, String city, String state, String zipCode);
    
    /**
     * Map address detail to DTO
     */
    AddressDetailDTO mapToDTO(AddressDetail addressDetail);
    
    /**
     * Map address detail DTO to entity
     */
    AddressDetail mapToEntity(AddressDetailDTO addressDetailDTO);
}
