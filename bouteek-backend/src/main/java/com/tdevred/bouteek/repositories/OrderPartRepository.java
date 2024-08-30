package com.tdevred.bouteek.repositories;

import com.tdevred.bouteek.authentication.entities.User;
import com.tdevred.bouteek.entities.Order;
import com.tdevred.bouteek.entities.OrderPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderPartRepository extends JpaRepository<OrderPart, Long> {
    List<OrderPart> findAllByOrderId(Long orderId);

    void deleteAllByOrderId(Long orderId);
}
