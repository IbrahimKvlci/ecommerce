package com.ibrahimkvlci.ecommerce.catalog.services;

import java.util.List;

import com.ibrahimkvlci.ecommerce.catalog.dto.FilterDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductSearchDTO;
import com.ibrahimkvlci.ecommerce.catalog.models.ProductDocument;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;

public interface SearchService {

    public DataResult<ProductSearchDTO> searchProducts(String keyword, List<FilterDTO> filters);

    public Result indexProduct(ProductDocument productDocument);
}
