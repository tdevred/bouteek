package com.tdevred.bouteek.entities;

import com.tdevred.bouteek.authentication.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class OrderPart {
    @Getter
    private @Id
    @GeneratedValue Long id;

    @Getter
    @Setter
    @ManyToOne
    private Order order;

    @Getter
    @Setter
    @ManyToOne
    private Product product;

    @Getter
    @Setter
    private Integer quantity;

    OrderPart() {}
    public OrderPart(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }
}
