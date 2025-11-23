package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.AddressDetail;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for AddressDetail operations
 */
public interface AddressDetailService {

    AddressDetailResponseDTO createAddressDetail(AddressDetailRequestDTO addressDetailDTO);

    Optional<AddressDetailResponseDTO> getAddressDetailById(Long id);

    List<AddressDetailResponseDTO> getAllAddressDetails();

    List<AddressDetailResponseDTO> getAddressDetailsOfCustomer();

    AddressDetailResponseDTO updateAddressDetail(Long id, AddressDetailRequestDTO addressDetailDTO);

    void deleteAddressDetail(Long id);

    AddressDetailResponseDTO mapToDTO(AddressDetail addressDetail);

    AddressDetail mapToEntity(AddressDetailRequestDTO addressDetailDTO);
}
