package com.ibrahimkvlci.ecommerce.shipping.services;

import com.ibrahimkvlci.ecommerce.shipping.dto.ShipmentDetailDTO;
import com.ibrahimkvlci.ecommerce.shipping.exceptions.ShipmentDetailNotFoundException;
import com.ibrahimkvlci.ecommerce.shipping.exceptions.ShipmentDetailValidationException;
import com.ibrahimkvlci.ecommerce.shipping.models.ShipmentDetail;
import com.ibrahimkvlci.ecommerce.shipping.repositories.ShipmentDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for ShipmentDetail operations.
 * Provides business logic for shipment management.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ShipmentDetailServiceImpl implements ShipmentDetailService {
    
    private final ShipmentDetailRepository shipmentDetailRepository;
    private final AddressDetailService addressDetailService;
    
    @Override
    public ShipmentDetailDTO createShipmentDetail(ShipmentDetailDTO shipmentDetailDTO) {
        // Validate that shipment doesn't already exist for this order
        if (shipmentExistsForOrder(shipmentDetailDTO.getOrderId())) {
            throw new ShipmentDetailValidationException("Shipment already exists for order ID: " + shipmentDetailDTO.getOrderId());
        }
        
        ShipmentDetail shipmentDetail = mapToEntity(shipmentDetailDTO);
        ShipmentDetail savedShipmentDetail = shipmentDetailRepository.save(shipmentDetail);
        return mapToDTO(savedShipmentDetail);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentDetailDTO> getAllShipmentDetails() {
        return shipmentDetailRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentDetailDTO> getShipmentDetailById(Long id) {
        return shipmentDetailRepository.findById(id)
                .map(this::mapToDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentDetailDTO> getShipmentDetailByOrderId(Long orderId) {
        return shipmentDetailRepository.findByOrderId(orderId)
                .map(this::mapToDTO);
    }
    
    @Override
    public ShipmentDetailDTO updateShipmentDetail(Long id, ShipmentDetailDTO shipmentDetailDTO) {
        ShipmentDetail existingShipmentDetail = shipmentDetailRepository.findById(id)
                .orElseThrow(() -> new ShipmentDetailNotFoundException(id));
        
        // Update fields
        existingShipmentDetail.setOrderId(shipmentDetailDTO.getOrderId());
        if (shipmentDetailDTO.getAddressDetail() != null) {
            existingShipmentDetail.setAddressDetail(addressDetailService.mapToEntity(shipmentDetailDTO.getAddressDetail()));
        }
        existingShipmentDetail.setShippingMethod(shipmentDetailDTO.getShippingMethod());
        
        ShipmentDetail updatedShipmentDetail = shipmentDetailRepository.save(existingShipmentDetail);
        return mapToDTO(updatedShipmentDetail);
    }
    
    @Override
    public void deleteShipmentDetail(Long id) {
        if (!shipmentDetailRepository.existsById(id)) {
            throw new ShipmentDetailNotFoundException(id);
        }
        shipmentDetailRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentDetailDTO> getShipmentDetailsByCustomerId(Long customerId) {
        return shipmentDetailRepository.findByCustomerId(customerId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentDetailDTO> getShipmentDetailsByShippingMethod(com.ibrahimkvlci.ecommerce.shipping.models.ShippingMethod shippingMethod) {
        return shipmentDetailRepository.findByShippingMethod(shippingMethod).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentDetailDTO> getShipmentDetailsByCity(String city) {
        return shipmentDetailRepository.findByCity(city).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentDetailDTO> getShipmentDetailsByState(String state) {
        return shipmentDetailRepository.findByState(state).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ShipmentDetailDTO> getShipmentDetailsByZipCode(String zipCode) {
        return shipmentDetailRepository.findByZipCode(zipCode).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean shipmentExistsForOrder(Long orderId) {
        return shipmentDetailRepository.existsByOrderId(orderId);
    }
    
    @Override
    public ShipmentDetailDTO mapToDTO(ShipmentDetail shipmentDetail) {
        ShipmentDetailDTO dto = new ShipmentDetailDTO();
        dto.setId(shipmentDetail.getId());
        dto.setOrderId(shipmentDetail.getOrderId());
        if (shipmentDetail.getAddressDetail() != null) {
            dto.setAddressDetail(addressDetailService.mapToDTO(shipmentDetail.getAddressDetail()));
        }
        dto.setShippingMethod(shipmentDetail.getShippingMethod());
        return dto;
    }
    
    @Override
    public ShipmentDetail mapToEntity(ShipmentDetailDTO shipmentDetailDTO) {
        ShipmentDetail shipmentDetail = new ShipmentDetail();
        shipmentDetail.setId(shipmentDetailDTO.getId());
        shipmentDetail.setOrderId(shipmentDetailDTO.getOrderId());
        if (shipmentDetailDTO.getAddressDetail() != null) {
            shipmentDetail.setAddressDetail(addressDetailService.mapToEntity(shipmentDetailDTO.getAddressDetail()));
        }
        shipmentDetail.setShippingMethod(shipmentDetailDTO.getShippingMethod());
        return shipmentDetail;
    }
}
