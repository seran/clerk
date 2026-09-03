package com.clerk.register.services;

import com.clerk.register.data.requests.ProductCreateRequest;
import com.clerk.register.data.requests.ProductRemoteFetchRequest;
import com.clerk.register.data.responses.ProductResponse;
import com.clerk.register.models.Product;
import com.clerk.register.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final RestClient outboundRestClient;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(ProductResponse::from).toList();
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        Product product = new Product(
                request.name(),
                request.description(),
                request.active() == null || request.active()
        );
        return ProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public ProductResponse patch(Long id, Map<String, Object> changes) throws JacksonException {
        Product product = findProductById(id);
        objectMapper.updateValue(product, changes);
        return ProductResponse.from(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional
    public ResponseEntity<String> fetchImage(ProductRemoteFetchRequest request) {
        log.info("Fetching image from {}", request.url());

        ResponseEntity<String> response = outboundRestClient.get()
                .uri(URI.create(request.url()))
                .retrieve()
                .onStatus(s -> true, (req, res) -> {})
                .toEntity(String.class);

        if (request.productId() != null) {
            Product product = findProductById(request.productId());
            product.setImageURL(response.getBody());
            productRepository.save(product);
        }

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }

    @Transactional
    public ProductResponse importMetadata(ProductRemoteFetchRequest request) throws JacksonException {
        log.info("Importing metadata for product {}", request.productId());

        Map<String, Object> metadata = outboundRestClient.get()
                .uri(URI.create(request.url()))
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {});

        Product product = findProductById(request.productId());
        objectMapper.updateValue(product, metadata);

        return ProductResponse.from(productRepository.save(product));
    }

}
