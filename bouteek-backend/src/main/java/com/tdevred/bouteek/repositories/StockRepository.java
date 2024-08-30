package com.tdevred.bouteek.repositories;

import com.tdevred.bouteek.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByProductId(Long productId);

    void deleteAllByProductId(Long productId);

    List<Stock> findAllByWarehouseId(long warehouseId);

    Optional<Stock> findByProductIdAndWarehouseId(long productId, long warehouseId);
}
