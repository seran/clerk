package com.clerk.register.listeners;

import com.clerk.register.data.responses.ValidationResponse;
import com.clerk.register.events.ProductCreatedEvent;
import com.clerk.register.intelligence.ClerkIntelligence;
import com.clerk.register.models.Product;
import com.clerk.register.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductProofreader {

    private final ProductRepository productRepository;

    private final ClerkIntelligence clerkIntelligence;

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 60)
    public void onProductCreated(ProductCreatedEvent event) {
        productRepository.findById(event.productId()).ifPresent(p -> {
            ValidationResponse response = clerkIntelligence.proofread(p.getDescription());
            if (!response.valid()) {
                log.info("Fixing product description for: {}", p.getId());
                p.setDescription(response.message());
            }

            p.setChecked(clerkIntelligence.verifyDescription(p));
            Product savedProduct = productRepository.save(p);
            clerkIntelligence.index(savedProduct);
        });
    }
}
