package com.tdevred.bouteek.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Category {
    @Getter
    private @Id
    @GeneratedValue Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    Category() {}
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return STR."Product{id=\{id}, name='\{name}\{'\''}, description='\{description}\{'\''}\{'}'}";
    }
}
