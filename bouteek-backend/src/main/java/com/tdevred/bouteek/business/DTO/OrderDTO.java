package com.tdevred.bouteek.business.DTO;

import com.tdevred.bouteek.entities.Order;
import com.tdevred.bouteek.entities.Product;
import lombok.Getter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderDTO {
    private static Logger logger = LoggerFactory.getLogger(OrderDTO.class);

    private List<OrderPartDTO> products;

    public static OrderDTO fromOrder(Order order) {
        OrderDTO thisOrderDto = new OrderDTO();
        order.getParts().forEach(v -> logger.error(v.toString()));
        thisOrderDto.products = order.getParts().stream().map(p ->  new OrderPartDTO(p.getProduct(), p.getQuantity())).collect(Collectors.toList());
        return thisOrderDto;
    }
}
