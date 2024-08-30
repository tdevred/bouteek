package com.tdevred.bouteek.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class Product {
    @Getter
    private @Id
    @GeneratedValue Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Double price;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @ManyToOne
    private Category category;

    Product() {}
    public Product(String name, Double price, String description, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    @Override
    public String toString() {
        return STR."Product{id=\{id}, name='\{name}\{'\''}, category=\{category}\{'}'}";
    }
}
