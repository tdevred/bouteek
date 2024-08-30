package com.tdevred.bouteek.business.exceptions;

public class NotEnoughQuantityForStockException extends Exception {
    private final long productId;
    private final long warehouseId;
    private final long initialQuantity;
    private final long quantity;

    public NotEnoughQuantityForStockException(long productId, long warehouseId, long initialQuantity, long quantity) {
        super(STR."Tried to reduce stock too much: \{quantity}, value is \{initialQuantity} for '\{productId}:\{warehouseId}'.");
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.initialQuantity = initialQuantity;
        this.quantity = quantity;
    }
}
