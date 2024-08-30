
package com.tdevred.bouteek.repositories;

import com.tdevred.bouteek.entities.Product;
import com.tdevred.bouteek.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
