package com.tdevred.bouteek;

import com.tdevred.bouteek.authentication.entities.User;
import com.tdevred.bouteek.authentication.services.AuthenticationService;
import com.tdevred.bouteek.business.DTO.*;
import com.tdevred.bouteek.business.OrderBusiness;
import com.tdevred.bouteek.business.StockBusiness;
import com.tdevred.bouteek.business.exceptions.NoCategoryException;
import com.tdevred.bouteek.business.exceptions.NoProductException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost"}, methods = {RequestMethod.GET, RequestMethod.POST}, allowedHeaders = {"Authorization", "Content-Type"})
@RestController
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderBusiness orderBusiness;
    private final AuthenticationService authenticationService;

    @Autowired
    public OrderController(OrderBusiness orderBusiness, AuthenticationService authenticationService) {
        this.orderBusiness = orderBusiness;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/orders")
    public List<OrderDTO> getMyOrders(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        Optional<User> user = authenticationService.getUserByEmail(userDetails.getUsername());
        if(user.isEmpty()) {
            throw new Exception("aaaa");
        }
        return orderBusiness.findAllOrdersForUser(user.get());
    }

    @PostMapping("/orders")
    public void createOrder(@AuthenticationPrincipal UserDetails userDetails, @RequestBody OrderCreationSerialized serializedOrder) throws Exception {
        Optional<User> user = authenticationService.getUserByEmail(userDetails.getUsername());
        if(user.isEmpty()) {
            throw new Exception("aaaa");
        }
        OrderCreationDTO order = serializedOrder.deserialize();
        orderBusiness.createOrder(user.get(), order.getProducts().stream().toList());
    }
}
