package com.ibrahimkvlci.ecommerce.catalog.services;

import com.ibrahimkvlci.ecommerce.catalog.dto.ProductSearchRequest;

import org.springframework.data.domain.Pageable;

import com.ibrahimkvlci.ecommerce.catalog.dto.ProductSearchDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.ProductDocument;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;

public interface SearchService {

    public DataResult<ProductSearchDTO> searchProducts(ProductSearchRequest productSearchRequest, Pageable pageable);

    public Result indexProduct(ProductDocument productDocument);

    public Result updateProduct(ProductDocument productDocument);

    public DataResult<ProductDocument> getProductById(Long id);
}
