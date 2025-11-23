package com.ibrahimkvlci.ecommerce.address.application;

import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailResponseDTO;
import com.ibrahimkvlci.ecommerce.address.services.AddressDetailService;
import com.ibrahimkvlci.ecommerce.address.services.CountryService;
import com.ibrahimkvlci.ecommerce.address.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class AddressApp {

    private final AddressDetailService addressDetailService;

    @Autowired
    public AddressApp(AddressDetailService addressDetailService,
            CountryService countryService,
            CityService cityService) {
        this.addressDetailService = addressDetailService;
    }

    public AddressDetailResponseDTO createAddressDetail(AddressDetailRequestDTO addressDetailDTO) {
        return addressDetailService.createAddressDetail(addressDetailDTO);
    }

    public Optional<AddressDetailResponseDTO> getAddressDetailById(Long id) {
        return addressDetailService.getAddressDetailById(id);
    }

    public List<AddressDetailResponseDTO> getAddressDetailsOfCustomer() {
        return addressDetailService.getAddressDetailsOfCustomer();
    }

    public List<AddressDetailResponseDTO> getAllAddressDetails() {
        return addressDetailService.getAllAddressDetails();
    }

    public AddressDetailResponseDTO updateAddressDetail(Long id, AddressDetailRequestDTO addressDetailDTO) {
        return addressDetailService.updateAddressDetail(id, addressDetailDTO);
    }

    public void deleteAddressDetail(Long id) {
        addressDetailService.deleteAddressDetail(id);
    }

}
