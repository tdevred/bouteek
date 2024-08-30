package com.tdevred.bouteek.business.exceptions;

public class NoCategoryException extends Exception {
    private final long categoryId;

    public NoCategoryException(long categoryId) {
        super("Tried to access category with ID '" + categoryId + "' which does not exist.");
        this.categoryId = categoryId;
    }
}
