package com.assignment.lotterysystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Shubham Kalaria
 * Exception definition for UserAlreadyExists
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
