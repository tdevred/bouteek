package com.tdevred.bouteek.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Warehouse {
    @Getter
    private @Id
    @GeneratedValue Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String address;

    Warehouse() {}

    public Warehouse(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return STR."Warehouse{id=\{id}, name='\{name}\{'\''}\{'}'}";
    }
}
