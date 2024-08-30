package com.tdevred.bouteek.business.DTO;

import com.tdevred.bouteek.entities.Product;
import com.tdevred.bouteek.entities.Stock;
import lombok.Getter;

public class StockDTO {

    @Getter
    private long id;

    @Getter
    private long productId;

    @Getter
    private long warehouseId;

    @Getter
    private long quantity;

    public StockDTO(long id, long productId, long warehouseId, long quantity) {
        this.id = id;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
    }

    public static StockDTO fromStock(Stock stock) {
        return new StockDTO(stock.getId(), stock.getProduct().getId(), stock.getWarehouse().getId(), stock.getQuantity());
    }
}
