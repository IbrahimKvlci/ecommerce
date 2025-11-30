package com.ibrahimkvlci.ecommerce.catalog.controllers;

import com.ibrahimkvlci.ecommerce.catalog.dto.BrandDTO;
import com.ibrahimkvlci.ecommerce.catalog.services.BrandService;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog/brands")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<DataResult<BrandDTO>> createBrand(@Valid @RequestBody BrandDTO brandDTO) {
        log.info("Creating new brand: {}", brandDTO.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(brandService.createBrand(brandService.mapToEntity(brandDTO)));
    }

    @GetMapping
    public ResponseEntity<DataResult<List<BrandDTO>>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<BrandDTO>> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<DataResult<BrandDTO>> getBrandByName(@PathVariable String name) {
        return ResponseEntity.ok(brandService.getBrandByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResult<BrandDTO>> updateBrand(@PathVariable Long id,
            @Valid @RequestBody BrandDTO brandDTO) {
        return ResponseEntity.ok(brandService.updateBrand(id, brandService.mapToEntity(brandDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteBrand(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.deleteBrand(id));
    }

    @GetMapping("/search/name")
    public ResponseEntity<DataResult<List<BrandDTO>>> searchBrandsByName(@RequestParam String name) {
        return ResponseEntity.ok(brandService.searchBrandsByName(name));
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByName(@RequestParam String name) {
        boolean exists = brandService.existsByName(name);
        return ResponseEntity.ok(exists);
    }
}
