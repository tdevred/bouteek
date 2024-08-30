package com.tdevred.bouteek.business.DTO;

import com.tdevred.bouteek.entities.Category;
import com.tdevred.bouteek.entities.Product;
import lombok.Getter;

public class ProductDTO {

    @Getter
    private long id;

    @Getter
    private String name;

    @Getter
    private double price;

    @Getter
    private String description;

    @Getter
    private String category;

    private ProductDTO(long id, String name, double price, String desciption, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = desciption;
        this.category = category;
    }

    public static ProductDTO fromProduct(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getDescription(), product.getCategory().getName());
    }

    public ProductDTO() {}

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", desciption='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public String toShortString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
