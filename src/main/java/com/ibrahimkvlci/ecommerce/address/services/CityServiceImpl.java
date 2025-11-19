package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.CityDTO;
import com.ibrahimkvlci.ecommerce.address.models.City;
import com.ibrahimkvlci.ecommerce.address.models.Country;
import com.ibrahimkvlci.ecommerce.address.repositories.CityRepository;
import com.ibrahimkvlci.ecommerce.address.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public City createCity(CityDTO cityDTO) {
        Country country = countryRepository.findById(Objects.requireNonNull(cityDTO.getCountryId()))
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + cityDTO.getCountryId()));

        City city = new City();
        city.setName(cityDTO.getName());
        city.setCountry(country);

        return cityRepository.save(city);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(Objects.requireNonNull(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> getAllCities() {
        return cityRepository.findAllWithCountry();
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> getCitiesByCountryId(Long countryId) {
        return cityRepository.findByCountryIdOrderedByName(countryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> searchCitiesByName(String name) {
        return cityRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> searchCitiesByCountryAndName(Long countryId, String name) {
        return cityRepository.findByCountryIdAndNameContainingIgnoreCase(countryId, name);
    }

    @Override
    public City updateCity(Long id, CityDTO cityDTO) {
        City city = cityRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));

        Country country = countryRepository.findById(Objects.requireNonNull(cityDTO.getCountryId()))
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + cityDTO.getCountryId()));

        city.setName(cityDTO.getName());
        city.setCountry(country);

        return cityRepository.save(city);
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
    public CityDTO mapToDTO(City city) {
        if (city == null)
            return null;

        CityDTO dto = new CityDTO();
        dto.setName(city.getName());
        dto.setCountryId(city.getCountry().getId());
        dto.setCountryName(city.getCountry().getName());
        return dto;
    }

    @Override
    public City mapToEntity(CityDTO cityDTO) {
        if (cityDTO == null)
            return null;

        City city = new City();
        city.setName(cityDTO.getName());
        // Country will be set in service methods
        return city;
    }
}
