package com.tdevred.bouteek.entities;

import com.tdevred.bouteek.authentication.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

@Table(name = "client_orders")
@Entity
public class Order {
    @Getter
    private @Id
    @GeneratedValue Long id;

    @Getter
    @Setter
    @ManyToOne
    private User user;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    private List<OrderPart> parts;

    @Getter
    private Boolean confirmed;

    Order() {}
    public Order(User user) {
        this.user = user;
        this.parts = new ArrayList<>();
        this.confirmed = false;
    }

    public void addPart(OrderPart orderPart) {
        this.parts.add(orderPart);
    }

    public void setConfirmed() {
        this.confirmed = true;
    }
}
