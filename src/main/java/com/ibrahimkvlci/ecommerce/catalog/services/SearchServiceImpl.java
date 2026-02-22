package com.ibrahimkvlci.ecommerce.catalog.services;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.GetResponse;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ibrahimkvlci.ecommerce.catalog.utilities.results.ErrorDataResult;
import com.ibrahimkvlci.ecommerce.catalog.dto.AttributeDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.AttributeValueDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.CategoryDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductDisplayDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductSearchDTO;
import com.ibrahimkvlci.ecommerce.catalog.dto.ProductSearchRequest;
import com.ibrahimkvlci.ecommerce.catalog.mappers.ProductMapper;
import com.ibrahimkvlci.ecommerce.catalog.models.ProductDocument;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessResult;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

        private final OpenSearchClient client;

        private final CategoryService categoryService;

        private final ProductMapper productMapper;

        private final JdbcTemplate jdbcTemplate;

        @Override
        public DataResult<ProductSearchDTO> searchProducts(ProductSearchRequest productSearchRequest,
                        Pageable pageable) {

                String keyword = productSearchRequest.getSearchTerm();
                List<Long> categoryIds = productSearchRequest.getCategoryIds();
                List<AttributeDTO> filters = productSearchRequest.getFilters();
                double minPrice = productSearchRequest.getMinPrice();
                double maxPrice = productSearchRequest.getMaxPrice();

                List<AttributeDTO> selectedFilters = filters.stream()
                                .filter(f -> f.getValues().stream().anyMatch(AttributeValueDTO::getIsSelected))
                                .collect(Collectors.toList());

                SearchResponse<ProductDocument> response;
                try {
                        response = client.search(s -> s
                                        .index("products")
                                        .query(q -> q.bool(b -> {

                                                b.filter(f -> f.nested(n -> n.path("inventories").query(
                                                                nq -> nq.range(r -> {
                                                                        r.field("inventories.stock")
                                                                                        .gt(JsonData.of(0));
                                                                        r.field("inventories.price")
                                                                                        .gte(JsonData.of(minPrice));
                                                                        r.field("inventories.price")
                                                                                        .lte(JsonData.of(maxPrice));
                                                                        return r;
                                                                }))));

                                                if (keyword != null && !keyword.isEmpty()) {
                                                        b.must(m -> m.multiMatch(mm -> mm
                                                                        .fields("title", "description")
                                                                        .query(keyword)));
                                                }

                                                if (categoryIds != null && !categoryIds.isEmpty()) {
                                                        b.filter(f -> f.terms(t -> t
                                                                        .field("category.id")
                                                                        .terms(ts -> ts.value(categoryIds.stream()
                                                                                        .map(FieldValue::of)
                                                                                        .collect(Collectors
                                                                                                        .toList())))));
                                                }

                                                if (selectedFilters != null) {
                                                        for (AttributeDTO filter : selectedFilters) {
                                                                String filterKey = filter.getKey();
                                                                List<AttributeValueDTO> filterValues = filter
                                                                                .getValues();

                                                                b.filter(f -> f.nested(n -> n
                                                                                .path("attributes")
                                                                                .query(nq -> nq.bool(nb -> nb
                                                                                                .must(m -> m.term(t -> t
                                                                                                                .field("attributes.key")
                                                                                                                .value(FieldValue
                                                                                                                                .of(filterKey))))
                                                                                                .must(m -> m.terms(
                                                                                                                t -> t
                                                                                                                                .field("attributes.value")
                                                                                                                                .terms(tt -> tt.value(
                                                                                                                                                filterValues.stream()
                                                                                                                                                                .map(av -> {

                                                                                                                                                                        if (av.getIsSelected()) {
                                                                                                                                                                                return FieldValue
                                                                                                                                                                                                .of(av.getValueText());
                                                                                                                                                                        }
                                                                                                                                                                        return null;
                                                                                                                                                                })
                                                                                                                                                                .filter(Objects::nonNull)
                                                                                                                                                                .collect(Collectors
                                                                                                                                                                                .toList())))))))));
                                                        }
                                                }

                                                return b;
                                        })).aggregations("top_categories", a -> a.terms(t -> t.field("category.id")))
                                        .size(10)
                                        .aggregations("attributes_nested", a -> a
                                                        .nested(n -> n.path("attributes"))
                                                        .aggregations("attr_keys", keyAgg -> keyAgg
                                                                        .terms(t -> t
                                                                                        .field("attributes.key")
                                                                                        .size(10))
                                                                        .aggregations("attr_values",
                                                                                        valueAgg -> valueAgg
                                                                                                        .terms(t -> t
                                                                                                                        .field("attributes.value")
                                                                                                                        .size(50)))))
                                        .from((int) pageable.getOffset())
                                        .size(pageable.getPageSize()),
                                        ProductDocument.class);
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }

                List<ProductDocument> productDocuments = response.hits().hits().stream()
                                .map(Hit::source)
                                .collect(Collectors.toList());

                List<CategoryDTO> categories = response.aggregations().get("top_categories").lterms().buckets()
                                .array().stream()
                                .map(b -> categoryService.getCategoryById(Long.parseLong(b.key())).getData())
                                .collect(Collectors.toList());

                List<AttributeDTO> attributeDTOs = response.aggregations().get("attributes_nested").nested()
                                .aggregations().get("attr_keys").sterms().buckets().array().stream()
                                .map(keyBucket -> {
                                        String key = keyBucket.key();

                                        List<AttributeValueDTO> values = keyBucket.aggregations().get("attr_values")
                                                        .sterms().buckets().array().stream()
                                                        .map(valueBucket -> new AttributeValueDTO(valueBucket.key(),
                                                                        valueBucket.docCount(), false))
                                                        .collect(Collectors.toList());

                                        return new AttributeDTO(key, values);
                                })
                                .collect(Collectors.toList());

                List<ProductDisplayDTO> productDisplayDTOs = productDocuments.stream()
                                .map(productMapper::toProductDisplayDTO)
                                .collect(Collectors.toList());

                return new SuccessDataResult<ProductSearchDTO>("Search successful",
                                new ProductSearchDTO(
                                                new PageImpl<>(productDisplayDTOs, pageable,
                                                                response.hits().total().value()),
                                                categories,
                                                (filters != null && !filters.isEmpty()) ? filters : attributeDTOs));

        }

        @Override
        public Result indexProduct(ProductDocument productDocument) {
                try {
                        client.index(i -> i
                                        .index("products")
                                        .id(productDocument.getId().toString())
                                        .document(productDocument));
                        refreshUniqueKeywords();
                        return new SuccessResult("Product indexed successfully");
                } catch (IOException e) {
                        throw new RuntimeException("Ürün indeksleme sırasında hata oluştu", e);
                }
        }

        @Override
        public Result updateProduct(ProductDocument productDocument) {
                try {
                        client.update(u -> u
                                        .index("products")
                                        .id(productDocument.getId().toString())
                                        .doc(productDocument),
                                        ProductDocument.class);
                        refreshUniqueKeywords();
                } catch (IOException e) {
                        throw new RuntimeException("Ürün güncelleme sırasında hata oluştu", e);
                }
                return new SuccessResult("Product updated successfully");
        }

        @Override
        public DataResult<ProductDocument> getProductById(Long id) {
                try {
                        GetResponse<ProductDocument> productDocument = client.get(g -> g
                                        .index("products")
                                        .id(id.toString()),
                                        ProductDocument.class);
                        if (productDocument.found()) {
                                return new SuccessDataResult<>("Product retrieved successfully",
                                                productDocument.source());
                        }
                        return new ErrorDataResult<>("Product not found", null);
                } catch (IOException e) {
                        throw new RuntimeException("Ürün alma sırasında hata oluştu", e);
                }
        }

        @Override
        public Result refreshUniqueKeywords() {
                String sql = "REFRESH MATERIALIZED VIEW unique_keywords";
                jdbcTemplate.execute(sql);
                return new SuccessResult("Unique keywords refreshed successfully");
        }

}