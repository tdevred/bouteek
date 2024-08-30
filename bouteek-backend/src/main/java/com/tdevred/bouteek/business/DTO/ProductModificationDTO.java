package com.tdevred.bouteek.business.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class ProductModificationDTO {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Double price;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Long category;
}
