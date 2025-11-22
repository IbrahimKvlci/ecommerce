package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.client.UserClient;
import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.AddressDetail;
import com.ibrahimkvlci.ecommerce.address.models.Country;
import com.ibrahimkvlci.ecommerce.address.models.City;
import com.ibrahimkvlci.ecommerce.address.models.District;
import com.ibrahimkvlci.ecommerce.address.models.Neighborhood;
import com.ibrahimkvlci.ecommerce.address.repositories.AddressDetailRepository;
import com.ibrahimkvlci.ecommerce.address.repositories.CountryRepository;
import com.ibrahimkvlci.ecommerce.address.repositories.CityRepository;
import com.ibrahimkvlci.ecommerce.address.repositories.DistrictRepository;
import com.ibrahimkvlci.ecommerce.address.repositories.NeighborhoodRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressDetailServiceImpl implements AddressDetailService {

    private final AddressDetailRepository addressDetailRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final NeighborhoodRepository neighborhoodRepository;
    private final UserClient userClient;

    @Override
    @Transactional
    public AddressDetailResponseDTO createAddressDetail(AddressDetailRequestDTO addressDetailDTO) {
        AddressDetail addressDetail = new AddressDetail();

        addressDetail.setCountry(countryRepository.getReferenceById(addressDetailDTO.getCountryId()));
        addressDetail.setCity(cityRepository.getReferenceById(addressDetailDTO.getCityId()));
        addressDetail.setDistrict(districtRepository.getReferenceById(addressDetailDTO.getDistrictId()));
        addressDetail.setNeighborhood(neighborhoodRepository.getReferenceById(addressDetailDTO.getNeighborhoodId()));

        addressDetail.setAddress(addressDetailDTO.getAddress());
        addressDetail.setName(addressDetailDTO.getName());
        addressDetail.setSurname(addressDetailDTO.getSurname());
        addressDetail.setPhone(addressDetailDTO.getPhone());
        addressDetail.setDefaultAddress(addressDetailDTO.isDefaultAddress());
        addressDetail.setCustomerId(userClient.getCustomerIdFromJWT());
        addressDetail.setAddressPostalCode(addressDetailDTO.getPostalCode());

        if (addressDetailDTO.isDefaultAddress()) {
            addressDetailRepository.unsetDefaultAddress(addressDetail.getCustomerId());
        }

        return mapToDTO(addressDetailRepository.save(addressDetail));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AddressDetailResponseDTO> getAddressDetailById(Long id) {
        return addressDetailRepository.findById(Objects.requireNonNull(id)).map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressDetailResponseDTO> getAllAddressDetails() {
        return addressDetailRepository.findAllWithFullHierarchy().stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDetailResponseDTO updateAddressDetail(Long id, AddressDetailRequestDTO addressDetailDTO) {
        AddressDetail addressDetail = addressDetailRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new RuntimeException("Address detail not found with id: " + id));

        Country country = countryRepository.findById(Objects.requireNonNull(addressDetailDTO.getCountryId()))
                .orElseThrow(() -> new RuntimeException("Country not found"));
        City city = cityRepository.findById(Objects.requireNonNull(addressDetailDTO.getCityId()))
                .orElseThrow(() -> new RuntimeException("City not found"));
        District district = districtRepository.findById(Objects.requireNonNull(addressDetailDTO.getDistrictId()))
                .orElseThrow(() -> new RuntimeException("District not found"));
        Neighborhood neighborhood = neighborhoodRepository
                .findById(Objects.requireNonNull(addressDetailDTO.getNeighborhoodId()))
                .orElseThrow(() -> new RuntimeException("Neighborhood not found"));

        addressDetail.setCountry(country);
        addressDetail.setCity(city);
        addressDetail.setDistrict(district);
        addressDetail.setNeighborhood(neighborhood);
        addressDetail.setAddress(addressDetailDTO.getAddress());
        addressDetail.setName(addressDetailDTO.getName());
        addressDetail.setSurname(addressDetailDTO.getSurname());
        addressDetail.setPhone(addressDetailDTO.getPhone());

        return mapToDTO(addressDetailRepository.save(addressDetail));
    }

    @Override
    public void deleteAddressDetail(Long id) {
        addressDetailRepository.deleteById(Objects.requireNonNull(id));
    }

    @Override
    public AddressDetailResponseDTO mapToDTO(AddressDetail addressDetail) {
        if (addressDetail == null)
            return null;

        AddressDetailResponseDTO dto = new AddressDetailResponseDTO();
        dto.setId(addressDetail.getId());
        dto.setCountryId(addressDetail.getCountry().getId());
        dto.setCountryName(addressDetail.getCountry().getName());
        dto.setCityId(addressDetail.getCity().getId());
        dto.setCityName(addressDetail.getCity().getName());
        dto.setDistrictId(addressDetail.getDistrict().getId());
        dto.setDistrictName(addressDetail.getDistrict().getName());
        dto.setNeighborhoodId(addressDetail.getNeighborhood().getId());
        dto.setNeighborhoodName(addressDetail.getNeighborhood().getName());
        dto.setAddress(addressDetail.getAddress());
        dto.setPostalCode(addressDetail.getAddressPostalCode());
        dto.setDefaultAddress(addressDetail.isDefaultAddress());
        dto.setName(addressDetail.getName());
        dto.setSurname(addressDetail.getSurname());
        dto.setPhone(addressDetail.getPhone());

        return dto;
    }

    @Override
    public AddressDetail mapToEntity(AddressDetailRequestDTO addressDetailDTO) {
        if (addressDetailDTO == null)
            return null;

        AddressDetail addressDetail = new AddressDetail();
        addressDetail.setAddress(addressDetailDTO.getAddress());
        addressDetail.setName(addressDetailDTO.getName());
        addressDetail.setSurname(addressDetailDTO.getSurname());
        addressDetail.setPhone(addressDetailDTO.getPhone());
        // Relations will be set in service methods

        return addressDetail;
    }

    @Override
    public List<AddressDetailResponseDTO> getAddressDetailsByCustomerId(Long id) {
        return addressDetailRepository.findAllByCustomerId(id).stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
