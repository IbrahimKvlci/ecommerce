package com.ibrahimkvlci.ecommerce.catalog.services;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.stereotype.Service;

import com.ibrahimkvlci.ecommerce.catalog.models.ProductDocument;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

        private final OpenSearchClient client;

        @Override
        public List<ProductDocument> searchProducts(String keyword, Map<String, List<String>> filters) {
                try {
                        SearchResponse<ProductDocument> response = client.search(s -> s
                                        .index("products")
                                        .query(q -> q.bool(b -> {

                                                if (keyword != null && !keyword.isEmpty()) {
                                                        b.must(m -> m.multiMatch(mm -> mm
                                                                        .fields("title", "description")
                                                                        .query(keyword)));
                                                }

                                                if (filters != null && !filters.isEmpty()) {
                                                        for (Map.Entry<String, List<String>> entry : filters
                                                                        .entrySet()) {
                                                                String filterKey = entry.getKey();
                                                                List<String> filterValues = entry.getValue();

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
                                                                                                                                                                .map(FieldValue::of)
                                                                                                                                                                .collect(Collectors
                                                                                                                                                                                .toList())))))))));
                                                        }
                                                }

                                                return b;
                                        })),
                                        ProductDocument.class);

                        return response.hits().hits().stream()
                                        .map(Hit::source)
                                        .collect(Collectors.toList());

                } catch (IOException e) {
                        throw new RuntimeException("Arama sırasında hata oluştu", e);
                }
        }
}