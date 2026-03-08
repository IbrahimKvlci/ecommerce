package com.ibrahimkvlci.ecommerce.catalog.config;

import org.apache.http.HttpHost;
import org.opensearch.client.RestClient;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class OpenSearchConfig {

    @Value("${spring.opensearch.uris}")
    private String opensearchUri;

    @Bean
    public OpenSearchClient openSearchClient() {
        final URI uri = URI.create(opensearchUri);
        final HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
        final RestClient restClient = RestClient.builder(host).build();
        final RestClientTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
        return new OpenSearchClient(transport);
    }
}