package com.tdevred.bouteek.business;

import com.tdevred.bouteek.authentication.entities.User;
import com.tdevred.bouteek.business.DTO.OrderDTO;
import com.tdevred.bouteek.entities.*;
import com.tdevred.bouteek.repositories.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderBusiness {

    Logger logger = LoggerFactory.getLogger(OrderBusiness.class);

    private final OrderRepository orderRepository;
    private final OrderPartRepository orderPartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderBusiness(OrderRepository orderRepository, OrderPartRepository orderPartRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderPartRepository = orderPartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void createOrder(User user, List<Pair<Long, Integer>> products) {
        Order order = new Order(user);
        orderRepository.save(order);

        List<Product> productList = productRepository.findAllById(products.stream().map(Pair::getLeft).collect(Collectors.toList()));

        AtomicInteger i = new AtomicInteger();
        productList.forEach(p -> {
            logger.info(p.getName());
        });

        List<Pair<Product, Integer>> productListFinal = products.stream().map(v -> {
            return new ImmutablePair<>(productList.get(i.getAndIncrement()), v.getRight());
        }).collect(Collectors.toList());

        productListFinal.forEach(part -> {
            OrderPart orderPart = new OrderPart(order, part.getLeft(), part.getRight());
            orderPartRepository.save(orderPart);
        });
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        this.orderPartRepository.deleteAllByOrderId(orderId);
        this.orderRepository.deleteById(orderId);
    }

    public void confirmOrder(Order order) {
        order.setConfirmed();
        orderRepository.save(order);
    }

    public List<OrderDTO> findAllOrdersForUser(User user) {
        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream().map(OrderDTO::fromOrder).collect(Collectors.toList());
    }
}
