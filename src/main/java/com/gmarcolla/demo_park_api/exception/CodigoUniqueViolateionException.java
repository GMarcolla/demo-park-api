package com.gmarcolla.demo_park_api.exception;

public class CodigoUniqueViolateionException extends RuntimeException {

    public CodigoUniqueViolateionException(String message) {
        super(message);
    }
}
