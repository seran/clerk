package com.clerk.register.config;

import com.clerk.register.models.License;
import com.clerk.register.models.Product;
import com.clerk.register.models.Role;
import com.clerk.register.models.User;
import com.clerk.register.repositories.LicenseRepository;
import com.clerk.register.repositories.ProductRepository;
import com.clerk.register.repositories.UserRepository;
import com.clerk.register.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final LicenseRepository licenseRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        User alice = createUser("alice", "password1", "alice@clerk.test", Role.USER);
        User bob = createUser("bob", "hunter2", "bob@clerk.test", Role.USER);
        createUser("admin", "admin123", "admin@clerk.test", Role.ADMIN);

        Product cad = new Product("Acme CAD", "2D/3D drafting suite", true);
        cad.setOwnerId(alice.getId());
        cad = productRepository.save(cad);

        Product ide = new Product("Beta IDE", "Polyglot code editor", true);
        ide.setOwnerId(bob.getId());
        ide = productRepository.save(ide);

        createLicense("ACME-0001-ALICE", cad, alice.getId(), "s3cr3t-alice-9d2f");
        createLicense("BETA-0002-BOB", ide, bob.getId(), "s3cr3t-bob-71ac");
    }

    private User createUser(String username, String password, String email, Role role) {
        return userService.createUser(new User(username, password, email, role));
    }

    private void createLicense(String key, Product product, Long userId, String activationSecret) {
        License license = new License(key, true);
        license.setProduct(product);
        license.setUserId(userId);
        license.setActivationSecret(activationSecret);
        licenseRepository.save(license);
    }
}
