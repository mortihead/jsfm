package ru.mortihead.exception;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(long product_id) {
        super(String.format("JS framework product is not found with id : '%s'", product_id));
    }
}
