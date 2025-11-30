package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.AddressDetail;
import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;

import java.util.List;

/**
 * Service interface for AddressDetail operations
 */
public interface AddressDetailService {

    DataResult<AddressDetailResponseDTO> createAddressDetail(AddressDetailRequestDTO addressDetailDTO);

    DataResult<AddressDetailResponseDTO> getAddressDetailById(Long id);

    DataResult<List<AddressDetailResponseDTO>> getAllAddressDetails();

    DataResult<List<AddressDetailResponseDTO>> getAddressDetailsOfCustomer();

    DataResult<AddressDetailResponseDTO> updateAddressDetail(Long id, AddressDetailRequestDTO addressDetailDTO);

    Result deleteAddressDetail(Long id);

    AddressDetailResponseDTO mapToDTO(AddressDetail addressDetail);

    AddressDetail mapToEntity(AddressDetailRequestDTO addressDetailDTO);
}
