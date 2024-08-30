package com.tdevred.bouteek.business.DTO;

import com.tdevred.bouteek.entities.Category;
import lombok.Getter;

public class CategoryDTO {
    @Getter
    private long id;

    @Getter
    private String name;

    @Getter
    private String description;

    private CategoryDTO(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public CategoryDTO() {}

    public static CategoryDTO fromCategory(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getDescription());
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String toShortString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
