package com.ibrahimkvlci.ecommerce.address.application;

import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CountryDTO;
import com.ibrahimkvlci.ecommerce.address.dto.CityDTO;
import com.ibrahimkvlci.ecommerce.address.models.AddressDetail;
import com.ibrahimkvlci.ecommerce.address.models.Country;
import com.ibrahimkvlci.ecommerce.address.models.City;
import com.ibrahimkvlci.ecommerce.address.services.AddressDetailService;
import com.ibrahimkvlci.ecommerce.address.services.CountryService;
import com.ibrahimkvlci.ecommerce.address.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Application service for Address operations.
 * Provides high-level business logic and orchestration for address management.
 */
@Service
@Transactional
public class AddressApp {
    
    private final AddressDetailService addressDetailService;
    private final CountryService countryService;
    private final CityService cityService;
    
    @Autowired
    public AddressApp(AddressDetailService addressDetailService,
                     CountryService countryService,
                     CityService cityService) {
        this.addressDetailService = addressDetailService;
        this.countryService = countryService;
        this.cityService = cityService;
    }
    
    // Address Detail Operations
    
    public AddressDetailDTO createAddressDetail(AddressDetailDTO addressDetailDTO) {
        AddressDetail addressDetail = addressDetailService.createAddressDetail(addressDetailDTO);
        return addressDetailService.mapToDTO(addressDetail);
    }
    
    @Transactional(readOnly = true)
    public Optional<AddressDetailDTO> getAddressDetailById(Long id) {
        return addressDetailService.getAddressDetailById(id)
                .map(addressDetailService::mapToDTO);
    }
    
    @Transactional(readOnly = true)
    public List<AddressDetailDTO> getAllAddressDetails() {
        return addressDetailService.getAllAddressDetails()
                .stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AddressDetailDTO> getAddressDetailsByCountryId(Long countryId) {
        return addressDetailService.getAddressDetailsByCountryId(countryId)
                .stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AddressDetailDTO> getAddressDetailsByCityId(Long cityId) {
        return addressDetailService.getAddressDetailsByCityId(cityId)
                .stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AddressDetailDTO> searchAddressDetailsByName(String name) {
        return addressDetailService.searchAddressDetailsByName(name)
                .stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AddressDetailDTO> searchAddressDetailsByPhone(String phone) {
        return addressDetailService.searchAddressDetailsByPhone(phone)
                .stream()
                .map(addressDetailService::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public AddressDetailDTO updateAddressDetail(Long id, AddressDetailDTO addressDetailDTO) {
        AddressDetail updatedAddressDetail = addressDetailService.updateAddressDetail(id, addressDetailDTO);
        return addressDetailService.mapToDTO(updatedAddressDetail);
    }
    
    public void deleteAddressDetail(Long id) {
        addressDetailService.deleteAddressDetail(id);
    }
    
    // Country Operations
    
    public CountryDTO createCountry(CountryDTO countryDTO) {
        Country country = countryService.createCountry(countryDTO);
        return countryService.mapToDTO(country);
    }
    
    @Transactional(readOnly = true)
    public Optional<CountryDTO> getCountryById(Long id) {
        return countryService.getCountryById(id)
                .map(countryService::mapToDTO);
    }
    
    @Transactional(readOnly = true)
    public List<CountryDTO> getAllCountries() {
        return countryService.getAllCountries()
                .stream()
                .map(countryService::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<CountryDTO> searchCountriesByName(String name) {
        return countryService.searchCountriesByName(name)
                .stream()
                .map(countryService::mapToDTO)
                .collect(Collectors.toList());
    }
    
    // City Operations
    
    public CityDTO createCity(CityDTO cityDTO) {
        City city = cityService.createCity(cityDTO);
        return cityService.mapToDTO(city);
    }
    
    @Transactional(readOnly = true)
    public Optional<CityDTO> getCityById(Long id) {
        return cityService.getCityById(id)
                .map(cityService::mapToDTO);
    }
    
    @Transactional(readOnly = true)
    public List<CityDTO> getAllCities() {
        return cityService.getAllCities()
                .stream()
                .map(cityService::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<CityDTO> getCitiesByCountryId(Long countryId) {
        return cityService.getCitiesByCountryId(countryId)
                .stream()
                .map(cityService::mapToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<CityDTO> searchCitiesByName(String name) {
        return cityService.searchCitiesByName(name)
                .stream()
                .map(cityService::mapToDTO)
                .collect(Collectors.toList());
    }
    
    // Combined Operations
    
    @Transactional(readOnly = true)
    public AddressSummary getAddressSummary() {
        long totalCountries = countryService.getAllCountries().size();
        long totalCities = cityService.getAllCities().size();
        long totalAddressDetails = addressDetailService.getAllAddressDetails().size();
        
        return new AddressSummary(totalCountries, totalCities, totalAddressDetails);
    }
    
    /**
     * Address summary class
     */
    public static class AddressSummary {
        private final long totalCountries;
        private final long totalCities;
        private final long totalAddressDetails;
        
        public AddressSummary(long totalCountries, long totalCities, long totalAddressDetails) {
            this.totalCountries = totalCountries;
            this.totalCities = totalCities;
            this.totalAddressDetails = totalAddressDetails;
        }
        
        public long getTotalCountries() {
            return totalCountries;
        }
        
        public long getTotalCities() {
            return totalCities;
        }
        
        public long getTotalAddressDetails() {
            return totalAddressDetails;
        }
    }
}
