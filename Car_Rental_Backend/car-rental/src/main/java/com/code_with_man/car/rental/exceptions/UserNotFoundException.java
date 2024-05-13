package com.code_with_man.car.rental.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException (String message) {
        super(message);
    }
}
