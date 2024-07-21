package com.casestudy.ecommerce.exception;

public class ItemsAlreadyExistsException extends Exception{
    public ItemsAlreadyExistsException(String message) {
        super(message);
    }
}
