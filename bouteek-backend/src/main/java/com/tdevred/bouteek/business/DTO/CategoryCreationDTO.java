package com.tdevred.bouteek.business.DTO;

import lombok.Getter;

public class CategoryCreationDTO {

    @Getter
    private String name;

    @Getter
    private String description;

    public CategoryCreationDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
