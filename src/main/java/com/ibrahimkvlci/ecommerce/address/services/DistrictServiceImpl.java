package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.DistrictRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.DistrictResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.City;
import com.ibrahimkvlci.ecommerce.address.models.District;
import com.ibrahimkvlci.ecommerce.address.repositories.CityRepository;
import com.ibrahimkvlci.ecommerce.address.repositories.DistrictRepository;
import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;
import com.ibrahimkvlci.ecommerce.address.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.SuccessResult;
import com.ibrahimkvlci.ecommerce.address.exceptions.CityNotFoundException;
import com.ibrahimkvlci.ecommerce.address.exceptions.DistrictNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, CityRepository cityRepository) {
        this.districtRepository = districtRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public DataResult<DistrictResponseDTO> createDistrict(DistrictRequestDTO districtRequestDTO) {
        City city = cityRepository.findById(Objects.requireNonNull(districtRequestDTO.getCityId()))
                .orElseThrow(() -> new CityNotFoundException(districtRequestDTO.getCityId()));

        District district = new District();
        district.setName(districtRequestDTO.getName());
        district.setCity(city);

        return new SuccessDataResult<>("District created successfully",
                mapToDTO(districtRepository.save(district)));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<DistrictResponseDTO> getDistrictById(Long id) {
        return new SuccessDataResult<>(districtRepository.findById(Objects.requireNonNull(id))
                .map(this::mapToDTO).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<DistrictResponseDTO>> getAllDistricts() {
        return new SuccessDataResult<>(districtRepository.findAllWithCityAndCountry().stream()
                .map(this::mapToDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<DistrictResponseDTO>> getDistrictsByCityId(Long cityId) {
        return new SuccessDataResult<>(districtRepository.findByCityIdOrderedByName(cityId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<DistrictResponseDTO>> searchDistrictsByName(String name) {
        return new SuccessDataResult<>(districtRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public DataResult<DistrictResponseDTO> updateDistrict(Long id, DistrictRequestDTO districtRequestDTO) {
        District district = districtRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new DistrictNotFoundException(id));

        City city = cityRepository.findById(Objects.requireNonNull(districtRequestDTO.getCityId()))
                .orElseThrow(() -> new CityNotFoundException(districtRequestDTO.getCityId()));

        district.setName(districtRequestDTO.getName());
        district.setCity(city);

        return new SuccessDataResult<>("District updated successfully",
                mapToDTO(districtRepository.save(district)));
    }

    @Override
    public Result deleteDistrict(Long id) {
        districtRepository.deleteById(Objects.requireNonNull(id));
        return new SuccessResult("District deleted successfully");
    }

    @Override
    public DistrictResponseDTO mapToDTO(District district) {
        if (district == null)
            return null;

        DistrictResponseDTO dto = new DistrictResponseDTO();
        dto.setId(district.getId());
        dto.setName(district.getName());
        dto.setCityId(district.getCity().getId());
        dto.setCityName(district.getCity().getName());
        dto.setCountryName(district.getCity().getCountry().getName());
        return dto;
    }

    @Override
    public District mapToEntity(DistrictRequestDTO districtRequestDTO) {
        if (districtRequestDTO == null)
            return null;

        District district = new District();
        district.setName(districtRequestDTO.getName());
        // City will be set in service methods
        return district;
    }
}
