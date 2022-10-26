package com.assignment.lotterysystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Shubham Kalaria
 * Exception definition for DataNotFound
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends Exception {
    public DataNotFoundException(String message) {
        super(message);
    }
}
