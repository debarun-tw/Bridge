package com.thoughtworks.kanjuice.restService.exceptions;

public class InvalidOrderTypeException extends Exception{
    public InvalidOrderTypeException(String message) {
        super(message);
    }
}
