package com.tdevred.bouteek.repositories;

import com.tdevred.bouteek.entities.Category;
import com.tdevred.bouteek.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String category_name);
}
