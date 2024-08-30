package com.tdevred.bouteek.repositories;

import com.tdevred.bouteek.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
