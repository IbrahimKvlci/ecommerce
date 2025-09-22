package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailDTO;
import com.ibrahimkvlci.ecommerce.address.models.AddressDetail;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for AddressDetail operations
 */
public interface AddressDetailService {
    
    AddressDetail createAddressDetail(AddressDetailDTO addressDetailDTO);
    Optional<AddressDetail> getAddressDetailById(Long id);
    List<AddressDetail> getAllAddressDetails();
    Optional<AddressDetail> getAddressDetailByCustomerId(Long id);
    AddressDetail updateAddressDetail(Long id, AddressDetailDTO addressDetailDTO);
    void deleteAddressDetail(Long id);
    AddressDetailDTO mapToDTO(AddressDetail addressDetail);
    AddressDetail mapToEntity(AddressDetailDTO addressDetailDTO);
}
