package com.ibrahimkvlci.ecommerce.shipping.services;

import com.ibrahimkvlci.ecommerce.shipping.dto.AddressDetailDTO;
import com.ibrahimkvlci.ecommerce.shipping.exceptions.AddressDetailNotFoundException;
import com.ibrahimkvlci.ecommerce.shipping.exceptions.AddressDetailValidationException;
import com.ibrahimkvlci.ecommerce.shipping.models.AddressDetail;
import com.ibrahimkvlci.ecommerce.shipping.repositories.AddressDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for AddressDetail operations.
 * Provides business logic for address management.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AddressDetailServiceImpl implements AddressDetailService {
    
    private final AddressDetailRepository addressDetailRepository;
    
    @Override
    public AddressDetailDTO createAddressDetail(AddressDetailDTO addressDetailDTO) {
        // Validate that the address doesn't already exist for this customer
        if (addressExistsForCustomer(
                addressDetailDTO.getCustomerId(),
                addressDetailDTO.getAddress(),
                addressDetailDTO.getCity(),
                addressDetailDTO.getState(),
                addressDetailDTO.getZipCode())) {
            throw new AddressDetailValidationException("Address already exists for this customer");
        }
        
        AddressDetail addressDetail = mapToEntity(addressDetailDTO);
        AddressDetail savedAddressDetail = addressDetailRepository.save(addressDetail);
        return mapToDTO(savedAddressDetail);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetailDTO> getAllAddressDetails() {
        return addressDetailRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<AddressDetailDTO> getAddressDetailById(Long id) {
        return addressDetailRepository.findById(id)
                .map(this::mapToDTO);
    }
    
    @Override
    public AddressDetailDTO updateAddressDetail(Long id, AddressDetailDTO addressDetailDTO) {
        AddressDetail existingAddressDetail = addressDetailRepository.findById(id)
                .orElseThrow(() -> new AddressDetailNotFoundException(id));
        
        // Update fields
        existingAddressDetail.setCustomerId(addressDetailDTO.getCustomerId());
        existingAddressDetail.setAddress(addressDetailDTO.getAddress());
        existingAddressDetail.setCity(addressDetailDTO.getCity());
        existingAddressDetail.setState(addressDetailDTO.getState());
        existingAddressDetail.setZipCode(addressDetailDTO.getZipCode());
        
        AddressDetail updatedAddressDetail = addressDetailRepository.save(existingAddressDetail);
        return mapToDTO(updatedAddressDetail);
    }
    
    @Override
    public void deleteAddressDetail(Long id) {
        if (!addressDetailRepository.existsById(id)) {
            throw new AddressDetailNotFoundException(id);
        }
        addressDetailRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetailDTO> getAddressDetailsByCustomerId(Long customerId) {
        return addressDetailRepository.findByCustomerId(customerId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetailDTO> getAddressDetailsByCity(String city) {
        return addressDetailRepository.findByCity(city).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetailDTO> getAddressDetailsByState(String state) {
        return addressDetailRepository.findByState(state).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetailDTO> getAddressDetailsByZipCode(String zipCode) {
        return addressDetailRepository.findByZipCode(zipCode).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetailDTO> getAddressDetailsByCityAndState(String city, String state) {
        return addressDetailRepository.findByCityAndState(city, state).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<AddressDetailDTO> getDefaultAddressByCustomerId(Long customerId) {
        return addressDetailRepository.findDefaultByCustomerId(customerId)
                .map(this::mapToDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean addressExistsForCustomer(Long customerId, String address, String city, String state, String zipCode) {
        return addressDetailRepository.existsByCustomerIdAndAddressAndCityAndStateAndZipCode(
                customerId, address, city, state, zipCode);
    }
    
    @Override
    public AddressDetailDTO mapToDTO(AddressDetail addressDetail) {
        AddressDetailDTO dto = new AddressDetailDTO();
        dto.setId(addressDetail.getId());
        dto.setCustomerId(addressDetail.getCustomerId());
        dto.setAddress(addressDetail.getAddress());
        dto.setCity(addressDetail.getCity());
        dto.setState(addressDetail.getState());
        dto.setZipCode(addressDetail.getZipCode());
        return dto;
    }
    
    @Override
    public AddressDetail mapToEntity(AddressDetailDTO addressDetailDTO) {
        AddressDetail addressDetail = new AddressDetail();
        addressDetail.setId(addressDetailDTO.getId());
        addressDetail.setCustomerId(addressDetailDTO.getCustomerId());
        addressDetail.setAddress(addressDetailDTO.getAddress());
        addressDetail.setCity(addressDetailDTO.getCity());
        addressDetail.setState(addressDetailDTO.getState());
        addressDetail.setZipCode(addressDetailDTO.getZipCode());
        return addressDetail;
    }
}
