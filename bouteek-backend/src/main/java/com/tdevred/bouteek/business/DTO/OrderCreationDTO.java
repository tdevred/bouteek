package com.tdevred.bouteek.business.DTO;

import lombok.Getter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderCreationDTO {

    private final List<Pair<Long, Integer>> products;

    public OrderCreationDTO() {
        products = new ArrayList<>();
    }

    public void addProduct(Long productId, int quantity) {
        products.add(new ImmutablePair<>(productId, quantity));
    }

}
