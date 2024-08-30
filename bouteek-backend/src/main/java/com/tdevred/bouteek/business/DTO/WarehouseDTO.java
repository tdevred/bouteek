package com.tdevred.bouteek.business.DTO;

import com.tdevred.bouteek.entities.Category;
import com.tdevred.bouteek.entities.Warehouse;
import lombok.Getter;

public class WarehouseDTO {
    @Getter
    private long id;

    @Getter
    private String name;

    @Getter
    private String desciption;

    private WarehouseDTO(long id, String name, String desciption) {
        this.id = id;
        this.name = name;
        this.desciption = desciption;
    }

    public static WarehouseDTO fromWarehouse(Warehouse warehouse) {
        return new WarehouseDTO(warehouse.getId(), warehouse.getName(), warehouse.getAddress());
    }

    @Override
    public String toString() {
        return "WarehouseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desciption='" + desciption + '\'' +
                '}';
    }

    public String toShortString() {
        return "WarehouseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
