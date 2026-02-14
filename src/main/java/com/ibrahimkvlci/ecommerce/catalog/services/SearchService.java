package com.ibrahimkvlci.ecommerce.catalog.services;

import java.util.List;

import com.ibrahimkvlci.ecommerce.catalog.dto.AttributeDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductSearchDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.ProductDocument;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;

public interface SearchService {

    public DataResult<ProductSearchDTO> searchProducts(String keyword, List<Long> categoryIds,
            List<AttributeDTO> filters);

    public Result indexProduct(ProductDocument productDocument);

    public Result updateProduct(ProductDocument productDocument);

    public DataResult<ProductDocument> getProductById(Long id);
}
