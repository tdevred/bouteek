package com.tdevred.bouteek.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Stock {
    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @ManyToOne
    private Product product;

    @Getter
    @Setter
    private Long quantity;

    @Getter
    @Setter
    @ManyToOne
    private Warehouse warehouse;


    public Stock() {
    }

    public Stock(Product product, Long quantity, Warehouse warehouse) {
        this.product = product;
        this.quantity = quantity;
        this.warehouse = warehouse;
    }

    @Override
    public String toString() {
        return STR."Stock{id=\{id}, product_id=\{product.getId()}, quantity=\{quantity}\{'}'}";
    }
}
