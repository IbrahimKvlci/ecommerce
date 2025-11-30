package com.ibrahimkvlci.ecommerce.address.services;

import com.ibrahimkvlci.ecommerce.address.dto.NeighborhoodRequestDTO;
import com.ibrahimkvlci.ecommerce.address.dto.NeighborhoodResponseDTO;
import com.ibrahimkvlci.ecommerce.address.models.District;
import com.ibrahimkvlci.ecommerce.address.models.Neighborhood;
import com.ibrahimkvlci.ecommerce.address.repositories.DistrictRepository;
import com.ibrahimkvlci.ecommerce.address.repositories.NeighborhoodRepository;
import com.ibrahimkvlci.ecommerce.address.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.Result;
import com.ibrahimkvlci.ecommerce.address.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.address.utilities.results.SuccessResult;
import com.ibrahimkvlci.ecommerce.address.exceptions.DistrictNotFoundException;
import com.ibrahimkvlci.ecommerce.address.exceptions.NeighborhoodNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
    public DataResult<NeighborhoodResponseDTO> createNeighborhood(NeighborhoodRequestDTO neighborhoodRequestDTO) {
        District district = districtRepository.findById(Objects.requireNonNull(neighborhoodRequestDTO.getDistrictId()))
                .orElseThrow(() -> new DistrictNotFoundException(neighborhoodRequestDTO.getDistrictId()));

        Neighborhood neighborhood = new Neighborhood();
        neighborhood.setName(neighborhoodRequestDTO.getName());
        neighborhood.setDistrict(district);

        return new SuccessDataResult<>("Neighborhood created successfully",
                mapToDTO(neighborhoodRepository.save(neighborhood)));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<NeighborhoodResponseDTO> getNeighborhoodById(Long id) {
        return new SuccessDataResult<>(neighborhoodRepository.findById(Objects.requireNonNull(id))
                .map(this::mapToDTO).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<NeighborhoodResponseDTO>> getAllNeighborhoods() {
        return new SuccessDataResult<>(neighborhoodRepository.findAllWithFullHierarchy().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<NeighborhoodResponseDTO>> getNeighborhoodsByDistrictId(Long districtId) {
        return new SuccessDataResult<>(neighborhoodRepository.findByDistrictIdOrderedByName(districtId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public DataResult<List<NeighborhoodResponseDTO>> searchNeighborhoodsByName(String name) {
        return new SuccessDataResult<>(neighborhoodRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public DataResult<NeighborhoodResponseDTO> updateNeighborhood(Long id,
            NeighborhoodRequestDTO neighborhoodRequestDTO) {
        Neighborhood neighborhood = neighborhoodRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new NeighborhoodNotFoundException(id));

        District district = districtRepository.findById(Objects.requireNonNull(neighborhoodRequestDTO.getDistrictId()))
                .orElseThrow(() -> new DistrictNotFoundException(neighborhoodRequestDTO.getDistrictId()));

        neighborhood.setName(neighborhoodRequestDTO.getName());
        neighborhood.setDistrict(district);

        return new SuccessDataResult<>("Neighborhood updated successfully",
                mapToDTO(neighborhoodRepository.save(neighborhood)));
    }

    @Override
    public Result deleteNeighborhood(Long id) {
        neighborhoodRepository.deleteById(Objects.requireNonNull(id));
        return new SuccessResult("Neighborhood deleted successfully");
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
