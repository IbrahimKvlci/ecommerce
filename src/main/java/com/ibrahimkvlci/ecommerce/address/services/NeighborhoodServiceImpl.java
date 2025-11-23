package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.NeighborhoodRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.NeighborhoodResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.District;
import com.ibrahimkvlci.ecommerce.address.models.Neighborhood;
import com.ibrahimkvlci.ecommerce.address.repositories.DistrictRepository;
import com.ibrahimkvlci.ecommerce.address.repositories.NeighborhoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class NeighborhoodServiceImpl implements NeighborhoodService {

    private final NeighborhoodRepository neighborhoodRepository;
    private final DistrictRepository districtRepository;

    @Autowired
    public NeighborhoodServiceImpl(NeighborhoodRepository neighborhoodRepository,
            DistrictRepository districtRepository) {
        this.neighborhoodRepository = neighborhoodRepository;
        this.districtRepository = districtRepository;
    }

    @Override
    public NeighborhoodResponseDTO createNeighborhood(NeighborhoodRequestDTO neighborhoodRequestDTO) {
        District district = districtRepository.findById(Objects.requireNonNull(neighborhoodRequestDTO.getDistrictId()))
                .orElseThrow(() -> new RuntimeException(
                        "District not found with id: " + neighborhoodRequestDTO.getDistrictId()));

        Neighborhood neighborhood = new Neighborhood();
        neighborhood.setName(neighborhoodRequestDTO.getName());
        neighborhood.setDistrict(district);

        return mapToDTO(neighborhoodRepository.save(neighborhood));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NeighborhoodResponseDTO> getNeighborhoodById(Long id) {
        return neighborhoodRepository.findById(Objects.requireNonNull(id)).map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NeighborhoodResponseDTO> getAllNeighborhoods() {
        return neighborhoodRepository.findAllWithFullHierarchy().stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NeighborhoodResponseDTO> getNeighborhoodsByDistrictId(Long districtId) {
        return neighborhoodRepository.findByDistrictIdOrderedByName(districtId).stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NeighborhoodResponseDTO> searchNeighborhoodsByName(String name) {
        return neighborhoodRepository.findByNameContainingIgnoreCase(name).stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NeighborhoodResponseDTO updateNeighborhood(Long id, NeighborhoodRequestDTO neighborhoodRequestDTO) {
        Neighborhood neighborhood = neighborhoodRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new RuntimeException("Neighborhood not found with id: " + id));

        District district = districtRepository.findById(Objects.requireNonNull(neighborhoodRequestDTO.getDistrictId()))
                .orElseThrow(() -> new RuntimeException(
                        "District not found with id: " + neighborhoodRequestDTO.getDistrictId()));

        neighborhood.setName(neighborhoodRequestDTO.getName());
        neighborhood.setDistrict(district);

        return mapToDTO(neighborhoodRepository.save(neighborhood));
    }

    @Override
    public void deleteNeighborhood(Long id) {
        neighborhoodRepository.deleteById(Objects.requireNonNull(id));
    }

    @Override
    public NeighborhoodResponseDTO mapToDTO(Neighborhood neighborhood) {
        if (neighborhood == null)
            return null;

        NeighborhoodResponseDTO dto = new NeighborhoodResponseDTO();
        dto.setId(neighborhood.getId());
        dto.setName(neighborhood.getName());
        dto.setDistrictId(neighborhood.getDistrict().getId());
        dto.setDistrictName(neighborhood.getDistrict().getName());
        dto.setCityName(neighborhood.getDistrict().getCity().getName());
        dto.setCountryName(neighborhood.getDistrict().getCity().getCountry().getName());
        return dto;
    }

    @Override
    public Neighborhood mapToEntity(NeighborhoodRequestDTO neighborhoodRequestDTO) {
        if (neighborhoodRequestDTO == null)
            return null;

        Neighborhood neighborhood = new Neighborhood();
        neighborhood.setName(neighborhoodRequestDTO.getName());
        // District will be set in service methods
        return neighborhood;
    }
}
