package com.ibrahimkvlci.ecommerce.address.application;

import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailDTO;
import com.ibrahimkvlci.ecommerce.address.models.AddressDetail;
import com.ibrahimkvlci.ecommerce.address.services.AddressDetailService;
import com.ibrahimkvlci.ecommerce.address.services.CountryService;
import com.ibrahimkvlci.ecommerce.address.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Application service for Address operations.
 * Provides high-level business logic and orchestration for address management.
 */
@Service
@Transactional
public class AddressApp {
    
    private final AddressDetailService addressDetailService;
    
    @Autowired
    public AddressApp(AddressDetailService addressDetailService,
                     CountryService countryService,
                     CityService cityService) {
        this.addressDetailService = addressDetailService;
    }
    
    // Address Detail Operations
    
    public AddressDetailDTO createAddressDetail(AddressDetailDTO addressDetailDTO) {
        AddressDetail addressDetail = addressDetailService.createAddressDetail(addressDetailDTO);
        return addressDetailService.mapToDTO(addressDetail);
    }
    
    @Transactional(readOnly = true)
    public Optional<AddressDetailDTO> getAddressDetailById(Long id) {
        return addressDetailService.getAddressDetailById(id)
                .map(addressDetailService::mapToDTO);
    }

    public Optional<AddressDetailDTO> getAddressDetailByCustomerId(Long id){
        return addressDetailService.getAddressDetailByCustomerId(id).map(addressDetailService::mapToDTO);
    }

    @Transactional(readOnly = true)
    public List<AddressDetailDTO> getAllAddressDetails() {
        return addressDetailService.getAllAddressDetails()
                .stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
    }
    
    
    public AddressDetailDTO updateAddressDetail(Long id, AddressDetailDTO addressDetailDTO) {
        AddressDetail updatedAddressDetail = addressDetailService.updateAddressDetail(id, addressDetailDTO);
        return addressDetailService.mapToDTO(updatedAddressDetail);
    }
    
    public void deleteAddressDetail(Long id) {
        addressDetailService.deleteAddressDetail(id);
    }
    
    
}
