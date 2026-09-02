package com.clerk.register.services;

import com.clerk.register.data.requests.ProductCreateRequest;
import com.clerk.register.data.responses.ProductResponse;
import com.clerk.register.models.Product;
import com.clerk.register.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
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

}
