package com.hasan.thesisProject.repository;

import com.hasan.thesisProject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
