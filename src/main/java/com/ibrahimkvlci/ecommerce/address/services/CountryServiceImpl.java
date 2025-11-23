package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.CountryRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CountryResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.Country;
import com.ibrahimkvlci.ecommerce.address.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public CountryResponseDTO createCountry(CountryRequestDTO countryRequestDTO) {
        Country country = mapToEntity(countryRequestDTO);
        return mapToDTO(countryRepository.save(Objects.requireNonNull(country)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CountryResponseDTO> getCountryById(Long id) {
        return countryRepository.findById(Objects.requireNonNull(id)).map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CountryResponseDTO> getCountryByName(String name) {
        return countryRepository.findByNameIgnoreCase(name).map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CountryResponseDTO> getCountryByCode(String code) {
        return countryRepository.findByCodeIgnoreCase(code).map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryResponseDTO> getAllCountries() {
        return countryRepository.findAllOrderedByName().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryResponseDTO> searchCountriesByName(String name) {
        return countryRepository.findByNameContainingIgnoreCase(name).stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CountryResponseDTO updateCountry(Long id, CountryRequestDTO countryRequestDTO) {
        Country country = countryRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));

        country.setName(countryRequestDTO.getName());
        country.setCode(countryRequestDTO.getCode());

        return mapToDTO(countryRepository.save(country));
    }

    @Override
    public void deleteCountry(Long id) {
        countryRepository.deleteById(Objects.requireNonNull(id));
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
