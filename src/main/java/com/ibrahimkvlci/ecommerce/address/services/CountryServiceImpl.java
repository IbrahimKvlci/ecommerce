package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.CountryRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CountryResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.Country;
import com.ibrahimkvlci.ecommerce.address.repositories.CountryRepository;
import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;
import com.ibrahimkvlci.ecommerce.address.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.SuccessResult;
import com.ibrahimkvlci.ecommerce.address.exceptions.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

/**
 * Implementation of CountryService
 */
@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public DataResult<CountryResponseDTO> createCountry(CountryRequestDTO countryRequestDTO) {
        Country country = mapToEntity(countryRequestDTO);
        return new SuccessDataResult<>("Country created successfully",
                mapToDTO(countryRepository.save(Objects.requireNonNull(country))));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<CountryResponseDTO> getCountryById(Long id) {
        return new SuccessDataResult<>(countryRepository.findById(Objects.requireNonNull(id))
                .map(this::mapToDTO).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<CountryResponseDTO> getCountryByName(String name) {
        return new SuccessDataResult<>(
                countryRepository.findByNameIgnoreCase(name).map(this::mapToDTO).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<CountryResponseDTO> getCountryByCode(String code) {
        return new SuccessDataResult<>(
                countryRepository.findByCodeIgnoreCase(code).map(this::mapToDTO).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<CountryResponseDTO>> getAllCountries() {
        return new SuccessDataResult<>(countryRepository.findAllOrderedByName().stream()
                .map(this::mapToDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<CountryResponseDTO>> searchCountriesByName(String name) {
        return new SuccessDataResult<>(countryRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public DataResult<CountryResponseDTO> updateCountry(Long id, CountryRequestDTO countryRequestDTO) {
        Country country = countryRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new CountryNotFoundException(id));

        country.setName(countryRequestDTO.getName());
        country.setCode(countryRequestDTO.getCode());

        return new SuccessDataResult<>("Country updated successfully",
                mapToDTO(countryRepository.save(country)));
    }

    @Override
    public Result deleteCountry(Long id) {
        countryRepository.deleteById(Objects.requireNonNull(id));
        return new SuccessResult("Country deleted successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return countryRepository.existsByNameIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCode(String code) {
        return countryRepository.existsByCodeIgnoreCase(code);
    }

    @Override
    public CountryResponseDTO mapToDTO(Country country) {
        if (country == null) {
            return null;
        }

        CountryResponseDTO dto = new CountryResponseDTO();
        dto.setId(country.getId());
        dto.setName(country.getName());
        dto.setCode(country.getCode());

        return dto;
    }

    @Override
    public Country mapToEntity(CountryRequestDTO countryRequestDTO) {
        if (countryRequestDTO == null) {
            return null;
        }

        Country country = new Country();
        country.setName(countryRequestDTO.getName());
        country.setCode(countryRequestDTO.getCode());

        return country;
    }
}
