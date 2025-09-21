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
    List<AddressDetail> getAddressDetailsByCountryId(Long countryId);
    List<AddressDetail> getAddressDetailsByCityId(Long cityId);
    List<AddressDetail> getAddressDetailsByDistrictId(Long districtId);
    List<AddressDetail> getAddressDetailsByNeighborhoodId(Long neighborhoodId);
    List<AddressDetail> searchAddressDetailsByName(String name);
    List<AddressDetail> searchAddressDetailsBySurname(String surname);
    List<AddressDetail> searchAddressDetailsByPhone(String phone);
    List<AddressDetail> searchAddressDetailsByAddress(String address);
    List<AddressDetail> searchAddressDetailsByNameAndSurname(String name, String surname);
    List<AddressDetail> getAddressDetailsByCountryAndCity(Long countryId, Long cityId);
    List<AddressDetail> getAddressDetailsByCountryCityAndDistrict(Long countryId, Long cityId, Long districtId);
    AddressDetail updateAddressDetail(Long id, AddressDetailDTO addressDetailDTO);
    void deleteAddressDetail(Long id);
    AddressDetailDTO mapToDTO(AddressDetail addressDetail);
    AddressDetail mapToEntity(AddressDetailDTO addressDetailDTO);
}
