package com.tdevred.bouteek.business.DTO;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

@Getter
public class OrderCreationSerialized {

    Logger logger = LoggerFactory.getLogger(OrderCreationSerialized.class);

    private JsonNode products;

    public OrderCreationDTO deserialize() {
        OrderCreationDTO orderCreationDeserialized = new OrderCreationDTO();
        for (Iterator<JsonNode> it = products.elements(); it.hasNext(); ) {
            JsonNode element = it.next();
            Long productId = element.get("productId").asLong();
            int quantity = element.get("quantity").asInt();
            orderCreationDeserialized.addProduct(productId, quantity);
        }

        return orderCreationDeserialized;
    }
}
