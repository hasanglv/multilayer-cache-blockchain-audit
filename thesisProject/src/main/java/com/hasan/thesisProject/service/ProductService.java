package com.hasan.thesisProject.service;

import com.hasan.thesisProject.dto.ProductRequestDTO;
import com.hasan.thesisProject.dto.ProductResponseDTO;
import com.hasan.thesisProject.entity.Product;
import com.hasan.thesisProject.repository.ProductRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, ProductResponseDTO> redisTemplate;
    private final BlockchainService blockchainService;

    // ================= L1 CACHE =================
    private final Map<String, ProductResponseDTO> l1Cache = new ConcurrentHashMap<>();

    private static final String PRODUCT_KEY_PREFIX = "products::";
    private static final Duration CACHE_TTL = Duration.ofMinutes(30);

    public ProductService(ProductRepository productRepository,
                          RedisTemplate<String, ProductResponseDTO> redisTemplate,
                          BlockchainService blockchainService) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
        this.blockchainService = blockchainService;
    }

    // =========================================================
    // ===================== ROLE RESOLVER =====================
    // =========================================================

    private int resolveCurrentUserRole() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return 0; // USER default
        }

        for (GrantedAuthority authority : authentication.getAuthorities()) {

            String role = authority.getAuthority();

            if (role.equals("ROLE_SUPER_ADMIN")) {
                return 2;
            }

            if (role.equals("ROLE_ADMIN")) {
                return 1;
            }
        }

        return 0; // USER
    }

    // =========================================================
    // ===================== FIND BY ID ========================
    // =========================================================

    public ProductResponseDTO findById(Long id) {

        String key = PRODUCT_KEY_PREFIX + id;
        ProductResponseDTO dto;

        // L1 CACHE
        if (l1Cache.containsKey(key)) {
            System.out.println(">>> Fetching from L1 CACHE <<<");
            dto = l1Cache.get(key);
        }

        // L2 REDIS
        else {
            ProductResponseDTO redisValue = redisTemplate.opsForValue().get(key);

            if (redisValue != null) {
                System.out.println(">>> Fetching from L2 REDIS <<<");
                l1Cache.put(key, redisValue);
                dto = redisValue;
            }

            // DATABASE
            else {
                System.out.println(">>> Fetching from DATABASE <<<");

                Product product = productRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                dto = mapToResponse(product);

                redisTemplate.opsForValue().set(key, dto, CACHE_TTL);
                l1Cache.put(key, dto);
            }
        }

        // 🔥 BLOCKCHAIN LOG (READ)
        try {
            blockchainService.logProductEvent(
                    0, // READ
                    resolveCurrentUserRole(),
                    id.toString(),
                    "ALLOWED"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    // =========================================================
    // ===================== FIND ALL ==========================
    // =========================================================

    public List<ProductResponseDTO> findAll() {

        System.out.println(">>> Fetching ALL from DATABASE <<<");

        List<ProductResponseDTO> list = productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

        for (ProductResponseDTO dto : list) {
            String key = PRODUCT_KEY_PREFIX + dto.getId();
            redisTemplate.opsForValue().set(key, dto, CACHE_TTL);
            l1Cache.put(key, dto);
        }

        return list;
    }

    // =========================================================
    // ===================== CREATE ============================
    // =========================================================

    public ProductResponseDTO create(ProductRequestDTO request) {

        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product saved = productRepository.save(product);
        ProductResponseDTO dto = mapToResponse(saved);

        // 🔥 BLOCKCHAIN LOG
        try {
            blockchainService.logProductEvent(
                    1, // CREATE
                    resolveCurrentUserRole(),
                    saved.getId().toString(),
                    "ALLOWED"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    // =========================================================
    // ===================== UPDATE ============================
    // =========================================================

    public ProductResponseDTO update(Long id, ProductRequestDTO request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product updated = productRepository.save(product);
        ProductResponseDTO dto = mapToResponse(updated);

        String key = PRODUCT_KEY_PREFIX + id;

        redisTemplate.opsForValue().set(key, dto, CACHE_TTL);
        l1Cache.put(key, dto);

        // 🔥 BLOCKCHAIN LOG
        try {
            blockchainService.logProductEvent(
                    2, // UPDATE
                    resolveCurrentUserRole(),
                    id.toString(),
                    "ALLOWED"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    // =========================================================
    // ===================== DELETE ============================
    // =========================================================

    public void delete(Long id) {

        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found");
        }

        productRepository.deleteById(id);

        String key = PRODUCT_KEY_PREFIX + id;

        redisTemplate.delete(key);
        l1Cache.remove(key);

        // 🔥 BLOCKCHAIN LOG
        try {
            blockchainService.logProductEvent(
                    3, // DELETE
                    resolveCurrentUserRole(),
                    id.toString(),
                    "ALLOWED"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================
    // ===================== MAPPER ============================
    // =========================================================

    private ProductResponseDTO mapToResponse(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock()
        );
    }
}