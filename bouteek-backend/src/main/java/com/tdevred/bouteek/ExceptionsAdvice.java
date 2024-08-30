package com.tdevred.bouteek;

import com.tdevred.bouteek.business.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsAdvice {
    @ExceptionHandler(NoProductException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productNotFoundHandler(NoProductException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NoCategoryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String categoryNotFoundHandler(NoCategoryException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NoWarehouseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String warehouseNotFoundHandler(NoWarehouseException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NoStockException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String stockNotFoundHandler(NoStockException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NotEnoughQuantityForStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String notEnoughQuantityHandler(NotEnoughQuantityForStockException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
