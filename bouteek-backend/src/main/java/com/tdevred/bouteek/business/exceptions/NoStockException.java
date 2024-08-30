package com.tdevred.bouteek.business.exceptions;

public class NoStockException extends Exception {
    private final long productId;
    private final long warehouseId;

    public NoStockException(long productId, long warehouseId) {
        super(STR."Tried to access stock with productId '\{productId}' and warehouseId '\{warehouseId}'which does not exist.");
        this.productId = productId;
        this.warehouseId = warehouseId;
    }
}
