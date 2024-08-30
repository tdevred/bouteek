package com.tdevred.bouteek.business.exceptions;

public class NoWarehouseException extends Exception {
    private final long warehouseId;

    public NoWarehouseException(long warehouseId) {
        super("Tried to access warehouse with ID '" + warehouseId + "' which does not exist.");
        this.warehouseId = warehouseId;
    }
}
