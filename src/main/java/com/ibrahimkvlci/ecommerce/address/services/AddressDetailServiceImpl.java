package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.client.UserClient;
import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailResponseDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CityResponseDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CountryResponseDTO;
import com.ibrahimkvlci.ecommerce.address.dto.DistrictResponseDTO;
import com.ibrahimkvlci.ecommerce.address.dto.NeighborhoodResponseDTO;
import com.ibrahimkvlci.ecommerce.address.exceptions.AuthException;
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
import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;
import com.ibrahimkvlci.ecommerce.address.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.SuccessResult;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public DataResult<AddressDetailResponseDTO> createAddressDetail(AddressDetailRequestDTO addressDetailDTO) {
        AddressDetail addressDetail = new AddressDetail();

        addressDetail.setCountry(
                countryRepository.getReferenceById(Objects.requireNonNull(addressDetailDTO.getCountryId())));
        addressDetail.setCity(cityRepository.getReferenceById(Objects.requireNonNull(addressDetailDTO.getCityId())));
        addressDetail.setDistrict(
                districtRepository.getReferenceById(Objects.requireNonNull(addressDetailDTO.getDistrictId())));
        addressDetail.setNeighborhood(
                neighborhoodRepository.getReferenceById(Objects.requireNonNull(addressDetailDTO.getNeighborhoodId())));

        addressDetail.setAddress(addressDetailDTO.getAddress());
        addressDetail.setName(addressDetailDTO.getName());
        addressDetail.setSurname(addressDetailDTO.getSurname());
        addressDetail.setPhone(addressDetailDTO.getPhone());
        addressDetail.setDefaultAddress(addressDetailDTO.isDefaultAddress());
        DataResult<Long> userIdRes = userClient.getCustomerIdFromJWT();
        if (!userIdRes.isSuccess()) {
            throw new AuthException("User not authenticated!");
        }
        Long customerId = userIdRes.getData();
        addressDetail.setCustomerId(customerId);
        addressDetail.setAddressPostalCode(addressDetailDTO.getPostalCode());
        addressDetail.setAddressTitle(addressDetailDTO.getAddressTitle());

        var res = addressDetailRepository.save(addressDetail);
        if (addressDetailDTO.isDefaultAddress()) {
            addressDetailRepository.unsetDefaultAddressExceptThis(addressDetail.getCustomerId(), res.getId());
        }
        return new SuccessDataResult<>("Address detail created successfully", mapToDTO(res));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<AddressDetailResponseDTO> getAddressDetailById(Long id) {
        return new SuccessDataResult<>(addressDetailRepository.findById(Objects.requireNonNull(id))
                .map(this::mapToDTO).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<AddressDetailResponseDTO>> getAllAddressDetails() {
        return new SuccessDataResult<>(
                addressDetailRepository.findAllWithFullHierarchy().stream().map(this::mapToDTO)
                        .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public DataResult<AddressDetailResponseDTO> updateAddressDetail(Long id, AddressDetailRequestDTO addressDetailDTO) {
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
        addressDetail.setName(addressDetailDTO.getName());
        addressDetail.setSurname(addressDetailDTO.getSurname());
        addressDetail.setPhone(addressDetailDTO.getPhone());
        addressDetail.setAddressTitle(addressDetailDTO.getAddressTitle());
        addressDetail.setAddressPostalCode(addressDetailDTO.getPostalCode());
        addressDetail.setAddress(addressDetailDTO.getAddress());

        if (addressDetailDTO.isDefaultAddress()) {
            addressDetailRepository.unsetDefaultAddressExceptThis(addressDetail.getCustomerId(), id);
        }

        addressDetail.setDefaultAddress(addressDetailDTO.isDefaultAddress());

        return new SuccessDataResult<>("Address detail updated successfully",
                mapToDTO(addressDetailRepository.save(addressDetail)));
    }

    @Override
    public Result deleteAddressDetail(Long id) {
        addressDetailRepository.deleteById(Objects.requireNonNull(id));
        return new SuccessResult("Address detail deleted successfully");
    }

    @Override
    public AddressDetailResponseDTO mapToDTO(AddressDetail addressDetail) {
        if (addressDetail == null)
            return null;

        AddressDetailResponseDTO dto = new AddressDetailResponseDTO();
        dto.setId(addressDetail.getId());

        CountryResponseDTO countryDTO = new CountryResponseDTO();
        countryDTO.setId(addressDetail.getCountry().getId());
        countryDTO.setName(addressDetail.getCountry().getName());
        countryDTO.setCode(addressDetail.getCountry().getCode());
        dto.setCountry(countryDTO);

        CityResponseDTO cityDTO = new CityResponseDTO();
        cityDTO.setId(addressDetail.getCity().getId());
        cityDTO.setName(addressDetail.getCity().getName());
        cityDTO.setCountryId(addressDetail.getCity().getCountry().getId());
        cityDTO.setCountryName(addressDetail.getCity().getCountry().getName());
        dto.setCity(cityDTO);

        DistrictResponseDTO districtDTO = new DistrictResponseDTO();
        districtDTO.setId(addressDetail.getDistrict().getId());
        districtDTO.setName(addressDetail.getDistrict().getName());
        districtDTO.setCityId(addressDetail.getDistrict().getCity().getId());
        districtDTO.setCityName(addressDetail.getDistrict().getCity().getName());
        districtDTO.setCountryName(addressDetail.getDistrict().getCity().getCountry().getName());
        dto.setDistrict(districtDTO);

        NeighborhoodResponseDTO neighborhoodDTO = new NeighborhoodResponseDTO();
        neighborhoodDTO.setId(addressDetail.getNeighborhood().getId());
        neighborhoodDTO.setName(addressDetail.getNeighborhood().getName());
        neighborhoodDTO.setDistrictId(addressDetail.getNeighborhood().getDistrict().getId());
        neighborhoodDTO.setDistrictName(addressDetail.getNeighborhood().getDistrict().getName());
        neighborhoodDTO.setCityName(addressDetail.getNeighborhood().getDistrict().getCity().getName());
        neighborhoodDTO.setCountryName(addressDetail.getNeighborhood().getDistrict().getCity().getCountry().getName());
        dto.setNeighborhood(neighborhoodDTO);

        dto.setAddress(addressDetail.getAddress());
        dto.setPostalCode(addressDetail.getAddressPostalCode());
        dto.setDefaultAddress(addressDetail.isDefaultAddress());
        dto.setName(addressDetail.getName());
        dto.setSurname(addressDetail.getSurname());
        dto.setPhone(addressDetail.getPhone());
        dto.setAddressTitle(addressDetail.getAddressTitle());

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
        addressDetail.setAddressTitle(addressDetailDTO.getAddressTitle());
        addressDetail.setAddressPostalCode(addressDetailDTO.getPostalCode());
        addressDetail.setDefaultAddress(addressDetailDTO.isDefaultAddress());
        // Relations will be set in service methods

        return addressDetail;
    }

    @Override
    public DataResult<List<AddressDetailResponseDTO>> getAddressDetailsOfCustomer() {
        DataResult<Long> userIdRes = userClient.getCustomerIdFromJWT();
        if (!userIdRes.isSuccess()) {
            throw new AuthException("User not authenticated!");
        }
        Long customerId = userIdRes.getData();
        return new SuccessDataResult<>(
                addressDetailRepository.findAllByCustomerIdOrderByIdAsc(customerId)
                        .stream()
                        .map(this::mapToDTO)
                        .collect(Collectors.toList()));
    }
}
