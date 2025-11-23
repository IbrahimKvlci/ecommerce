package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.CityRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CityResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.City;
import com.ibrahimkvlci.ecommerce.address.models.Country;
import com.ibrahimkvlci.ecommerce.address.repositories.CityRepository;
import com.ibrahimkvlci.ecommerce.address.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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

    public CityResponseDTO createCity(CityRequestDTO cityRequestDTO) {
        Country country = countryRepository.findById(Objects.requireNonNull(cityRequestDTO.getCountryId()))
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + cityRequestDTO.getCountryId()));

        City city = new City();
        city.setName(cityRequestDTO.getName());
        city.setCountry(country);

        return mapToDTO(cityRepository.save(city));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CityResponseDTO> getCityById(Long id) {
        return cityRepository.findById(Objects.requireNonNull(id)).map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityResponseDTO> getAllCities() {
        return cityRepository.findAllWithCountry().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityResponseDTO> getCitiesByCountryId(Long countryId) {
        return cityRepository.findByCountryIdOrderedByName(countryId).stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityResponseDTO> searchCitiesByName(String name) {
        return cityRepository.findByNameContainingIgnoreCase(name).stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityResponseDTO> searchCitiesByCountryAndName(Long countryId, String name) {
        return cityRepository.findByCountryIdAndNameContainingIgnoreCase(countryId, name).stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override

    public CityResponseDTO updateCity(Long id, CityRequestDTO cityRequestDTO) {
        City city = cityRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));

        Country country = countryRepository.findById(Objects.requireNonNull(cityRequestDTO.getCountryId()))
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + cityRequestDTO.getCountryId()));

        city.setName(cityRequestDTO.getName());
        city.setCountry(country);

        return mapToDTO(cityRepository.save(city));
    }

    @Override
    public void deleteCity(Long id) {
        cityRepository.deleteById(Objects.requireNonNull(id));
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
