package com.tdevred.bouteek.repositories;

import com.tdevred.bouteek.authentication.entities.User;
import com.tdevred.bouteek.entities.Category;
import com.tdevred.bouteek.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
