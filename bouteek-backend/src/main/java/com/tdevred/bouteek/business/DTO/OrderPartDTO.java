package com.tdevred.bouteek.business.DTO;

import com.tdevred.bouteek.entities.Product;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderPartDTO {

    private Long productId;
    private Integer quantity;

    public OrderPartDTO(Product product, Integer quantity) {
        this.productId = product.getId();
        this.quantity = quantity;
    }
}
