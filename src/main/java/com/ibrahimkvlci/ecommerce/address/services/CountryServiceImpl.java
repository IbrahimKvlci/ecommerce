package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.CountryDTO;
import com.ibrahimkvlci.ecommerce.address.models.Country;
import com.ibrahimkvlci.ecommerce.address.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Country createCountry(CountryDTO countryDTO) {
        Country country = mapToEntity(countryDTO);
        return countryRepository.save(country);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Country> getCountryByName(String name) {
        return countryRepository.findByNameIgnoreCase(name);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Country> getCountryByCode(String code) {
        return countryRepository.findByCodeIgnoreCase(code);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Country> getAllCountries() {
        return countryRepository.findAllOrderedByName();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Country> searchCountriesByName(String name) {
        return countryRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Override
    public Country updateCountry(Long id, CountryDTO countryDTO) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));
        
        country.setName(countryDTO.getName());
        country.setCode(countryDTO.getCode());
        
        return countryRepository.save(country);
    }
    
    @Override
    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
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
    public CountryDTO mapToDTO(Country country) {
        if (country == null) {
            return null;
        }
        
        CountryDTO dto = new CountryDTO();
        dto.setName(country.getName());
        dto.setCode(country.getCode());
        
        return dto;
    }
    
    @Override
    public Country mapToEntity(CountryDTO countryDTO) {
        if (countryDTO == null) {
            return null;
        }
        
        Country country = new Country();
        country.setName(countryDTO.getName());
        country.setCode(countryDTO.getCode());
        
        return country;
    }
}
