package com.tdevred.bouteek.business.DTO;

import com.tdevred.bouteek.entities.Product;
import lombok.Getter;
import lombok.Setter;

public class ProductCreationDTO {

    @Getter
    private String name;

    @Getter
    private double price;

    @Getter
    private String description;

    @Getter
    @Setter
    private long category;

    public ProductCreationDTO(String name, double price, String description, long category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }
}
