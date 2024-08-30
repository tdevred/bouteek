package com.tdevred.bouteek.business.exceptions;

public class NoProductException extends Exception {
    private final long productId;

    public NoProductException(long productId) {
        super("Tried to access category with ID '" + productId + "' which does not exist.");
        this.productId = productId;
    }
}
