package com.ibrahimkvlci.ecommerce.catalog.services;

import org.springframework.web.multipart.MultipartFile;

import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;

public interface ProductImageService {

    DataResult<String> uploadProductImage(MultipartFile file, Product product);

    Result deleteProductImage(Long imageId);

    Result setPrimaryImage(Long imageId);
}
