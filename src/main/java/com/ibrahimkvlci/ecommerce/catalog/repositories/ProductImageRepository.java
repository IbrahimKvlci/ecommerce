package com.ibrahimkvlci.ecommerce.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibrahimkvlci.ecommerce.catalog.models.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
