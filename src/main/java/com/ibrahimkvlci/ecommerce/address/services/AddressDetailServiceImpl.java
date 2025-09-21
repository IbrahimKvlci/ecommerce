package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.AddressDetailDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressDetailServiceImpl implements AddressDetailService {
    
    private final AddressDetailRepository addressDetailRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final NeighborhoodRepository neighborhoodRepository;
    
    @Autowired
    public AddressDetailServiceImpl(AddressDetailRepository addressDetailRepository,
                                  CountryRepository countryRepository,
                                  CityRepository cityRepository,
                                  DistrictRepository districtRepository,
                                  NeighborhoodRepository neighborhoodRepository) {
        this.addressDetailRepository = addressDetailRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.neighborhoodRepository = neighborhoodRepository;
    }
    
    @Override
    public AddressDetail createAddressDetail(AddressDetailDTO addressDetailDTO) {
        Country country = countryRepository.findById(addressDetailDTO.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found"));
        City city = cityRepository.findById(addressDetailDTO.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found"));
        District district = districtRepository.findById(addressDetailDTO.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District not found"));
        Neighborhood neighborhood = neighborhoodRepository.findById(addressDetailDTO.getNeighborhoodId())
                .orElseThrow(() -> new RuntimeException("Neighborhood not found"));
        
        AddressDetail addressDetail = new AddressDetail();
        addressDetail.setCountry(country);
        addressDetail.setCity(city);
        addressDetail.setDistrict(district);
        addressDetail.setNeighborhood(neighborhood);
        addressDetail.setAddress(addressDetailDTO.getAddress());
        addressDetail.setName(addressDetailDTO.getName());
        addressDetail.setSurname(addressDetailDTO.getSurname());
        addressDetail.setPhone(addressDetailDTO.getPhone());
        
        return addressDetailRepository.save(addressDetail);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<AddressDetail> getAddressDetailById(Long id) {
        return addressDetailRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetail> getAllAddressDetails() {
        return addressDetailRepository.findAllWithFullHierarchy();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetail> getAddressDetailsByCountryId(Long countryId) {
        return addressDetailRepository.findByCountryId(countryId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetail> getAddressDetailsByCityId(Long cityId) {
        return addressDetailRepository.findByCityId(cityId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetail> getAddressDetailsByDistrictId(Long districtId) {
        return addressDetailRepository.findByDistrictId(districtId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetail> getAddressDetailsByNeighborhoodId(Long neighborhoodId) {
        return addressDetailRepository.findByNeighborhoodId(neighborhoodId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetail> searchAddressDetailsByName(String name) {
        return addressDetailRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetail> searchAddressDetailsBySurname(String surname) {
        return addressDetailRepository.findBySurnameContainingIgnoreCase(surname);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetail> searchAddressDetailsByPhone(String phone) {
        return addressDetailRepository.findByPhoneContaining(phone);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetail> searchAddressDetailsByAddress(String address) {
        return addressDetailRepository.findByAddressContainingIgnoreCase(address);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetail> searchAddressDetailsByNameAndSurname(String name, String surname) {
        return addressDetailRepository.findByNameAndSurnameContaining(name, surname);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetail> getAddressDetailsByCountryAndCity(Long countryId, Long cityId) {
        return addressDetailRepository.findByCountryAndCity(countryId, cityId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AddressDetail> getAddressDetailsByCountryCityAndDistrict(Long countryId, Long cityId, Long districtId) {
        return addressDetailRepository.findByCountryCityAndDistrict(countryId, cityId, districtId);
    }
    
    @Override
    public AddressDetail updateAddressDetail(Long id, AddressDetailDTO addressDetailDTO) {
        AddressDetail addressDetail = addressDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address detail not found with id: " + id));
        
        Country country = countryRepository.findById(addressDetailDTO.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found"));
        City city = cityRepository.findById(addressDetailDTO.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found"));
        District district = districtRepository.findById(addressDetailDTO.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District not found"));
        Neighborhood neighborhood = neighborhoodRepository.findById(addressDetailDTO.getNeighborhoodId())
                .orElseThrow(() -> new RuntimeException("Neighborhood not found"));
        
        addressDetail.setCountry(country);
        addressDetail.setCity(city);
        addressDetail.setDistrict(district);
        addressDetail.setNeighborhood(neighborhood);
        addressDetail.setAddress(addressDetailDTO.getAddress());
        addressDetail.setName(addressDetailDTO.getName());
        addressDetail.setSurname(addressDetailDTO.getSurname());
        addressDetail.setPhone(addressDetailDTO.getPhone());
        
        return addressDetailRepository.save(addressDetail);
    }
    
    @Override
    public void deleteAddressDetail(Long id) {
        addressDetailRepository.deleteById(id);
    }
    
    @Override
    public AddressDetailDTO mapToDTO(AddressDetail addressDetail) {
        if (addressDetail == null) return null;
        
        AddressDetailDTO dto = new AddressDetailDTO();
        dto.setCountryId(addressDetail.getCountry().getId());
        dto.setCountryName(addressDetail.getCountry().getName());
        dto.setCityId(addressDetail.getCity().getId());
        dto.setCityName(addressDetail.getCity().getName());
        dto.setDistrictId(addressDetail.getDistrict().getId());
        dto.setDistrictName(addressDetail.getDistrict().getName());
        dto.setNeighborhoodId(addressDetail.getNeighborhood().getId());
        dto.setNeighborhoodName(addressDetail.getNeighborhood().getName());
        dto.setAddress(addressDetail.getAddress());
        dto.setName(addressDetail.getName());
        dto.setSurname(addressDetail.getSurname());
        dto.setPhone(addressDetail.getPhone());
        
        return dto;
    }
    
    @Override
    public AddressDetail mapToEntity(AddressDetailDTO addressDetailDTO) {
        if (addressDetailDTO == null) return null;
        
        AddressDetail addressDetail = new AddressDetail();
        addressDetail.setAddress(addressDetailDTO.getAddress());
        addressDetail.setName(addressDetailDTO.getName());
        addressDetail.setSurname(addressDetailDTO.getSurname());
        addressDetail.setPhone(addressDetailDTO.getPhone());
        // Relations will be set in service methods
        
        return addressDetail;
    }
}
