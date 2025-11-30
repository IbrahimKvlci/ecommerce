package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.CityRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CityResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.City;
import com.ibrahimkvlci.ecommerce.address.models.Country;
import com.ibrahimkvlci.ecommerce.address.repositories.CityRepository;
import com.ibrahimkvlci.ecommerce.address.repositories.CountryRepository;
import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;
import com.ibrahimkvlci.ecommerce.address.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.SuccessResult;
import com.ibrahimkvlci.ecommerce.address.exceptions.CityNotFoundException;
import com.ibrahimkvlci.ecommerce.address.exceptions.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public DataResult<CityResponseDTO> createCity(CityRequestDTO cityRequestDTO) {
        Country country = countryRepository.findById(Objects.requireNonNull(cityRequestDTO.getCountryId()))
                .orElseThrow(() -> new CountryNotFoundException(cityRequestDTO.getCountryId()));

        City city = new City();
        city.setName(cityRequestDTO.getName());
        city.setCountry(country);

        return new SuccessDataResult<>("City created successfully", mapToDTO(cityRepository.save(city)));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<CityResponseDTO> getCityById(Long id) {
        return new SuccessDataResult<>(
                cityRepository.findById(Objects.requireNonNull(id)).map(this::mapToDTO).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<CityResponseDTO>> getAllCities() {
        return new SuccessDataResult<>(cityRepository.findAllWithCountry().stream()
                .map(this::mapToDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<CityResponseDTO>> getCitiesByCountryId(Long countryId) {
        return new SuccessDataResult<>(
                cityRepository.findByCountryIdOrderedByName(countryId).stream().map(this::mapToDTO)
                        .collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<CityResponseDTO>> searchCitiesByName(String name) {
        return new SuccessDataResult<>(
                cityRepository.findByNameContainingIgnoreCase(name).stream().map(this::mapToDTO)
                        .collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<CityResponseDTO>> searchCitiesByCountryAndName(Long countryId, String name) {
        return new SuccessDataResult<>(cityRepository.findByCountryIdAndNameContainingIgnoreCase(countryId, name)
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public DataResult<CityResponseDTO> updateCity(Long id, CityRequestDTO cityRequestDTO) {
        City city = cityRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new CityNotFoundException(id));

        Country country = countryRepository.findById(Objects.requireNonNull(cityRequestDTO.getCountryId()))
                .orElseThrow(() -> new CountryNotFoundException(cityRequestDTO.getCountryId()));

        city.setName(cityRequestDTO.getName());
        city.setCountry(country);

        return new SuccessDataResult<>("City updated successfully", mapToDTO(cityRepository.save(city)));
    }

    @Override
    public Result deleteCity(Long id) {
        cityRepository.deleteById(Objects.requireNonNull(id));
        return new SuccessResult("City deleted successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNameAndCountryId(String name, Long countryId) {
        return cityRepository.existsByNameAndCountryId(name, countryId);
    }

    @Override
    public CityResponseDTO mapToDTO(City city) {
        if (city == null)
            return null;

        CityResponseDTO dto = new CityResponseDTO();
        dto.setId(city.getId());
        dto.setName(city.getName());
        dto.setCountryId(city.getCountry().getId());
        dto.setCountryName(city.getCountry().getName());
        return dto;
    }

    @Override
    public City mapToEntity(CityRequestDTO cityRequestDTO) {
        if (cityRequestDTO == null)
            return null;

        City city = new City();
        city.setName(cityRequestDTO.getName());
        // Country will be set in service methods
        return city;
    }
}
