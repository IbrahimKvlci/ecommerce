package com.ibrahimkvlci.ecommerce.catalog.services;

import java.util.List;
import java.util.Map;

import com.ibrahimkvlci.ecommerce.catalog.models.ProductDocument;

public interface SearchService {

    public List<ProductDocument> searchProducts(String keyword, Map<String, List<String>> filters);
}
