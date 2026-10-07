package com.ecommerce.config;

import com.ecommerce.model.*;
import com.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Database already seeded — skipping.");
            return;
        }

        log.info("Seeding database...");

        // ── Users ────────────────────────────────────
        User admin = userRepository.save(User.builder()
                .name("Admin User")
                .email("admin@store.com")
                .passwordHash(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .phone("1000000000")
                .build());

        User seller = userRepository.save(User.builder()
                .name("Seller One")
                .email("seller@store.com")
                .passwordHash(passwordEncoder.encode("seller123"))
                .role(Role.SELLER)
                .phone("2000000000")
                .build());

        User buyer = userRepository.save(User.builder()
                .name("Buyer One")
                .email("buyer@store.com")
                .passwordHash(passwordEncoder.encode("buyer123"))
                .role(Role.BUYER)
                .phone("3000000000")
                .build());

        // ── Categories ───────────────────────────────
        Category electronics = categoryRepository.save(Category.builder().name("Electronics").build());
        Category clothing    = categoryRepository.save(Category.builder().name("Clothing").build());
        Category books       = categoryRepository.save(Category.builder().name("Books").build());

        // ── Products (3 by the seed seller) ──────────
        productRepository.save(Product.builder()
                .seller(seller)
                .category(electronics)
                .name("Wireless Headphones")
                .description("Noise-cancelling over-ear headphones with 30-hour battery life.")
                .price(new BigDecimal("79.99"))
                .stockQty(50)
                .imageUrl("")
                .status("ACTIVE")
                .build());

        productRepository.save(Product.builder()
                .seller(seller)
                .category(clothing)
                .name("Cotton Crew-Neck T-Shirt")
                .description("Comfortable 100% cotton tee available in multiple sizes.")
                .price(new BigDecimal("19.99"))
                .stockQty(200)
                .imageUrl("")
                .status("ACTIVE")
                .build());

        productRepository.save(Product.builder()
                .seller(seller)
                .category(books)
                .name("Clean Code")
                .description("A handbook of agile software craftsmanship by Robert C. Martin.")
                .price(new BigDecimal("34.50"))
                .stockQty(80)
                .imageUrl("")
                .status("ACTIVE")
                .build());

        log.info("Seeding complete — 1 admin, 1 seller (3 products), 1 buyer.");
    }
}
