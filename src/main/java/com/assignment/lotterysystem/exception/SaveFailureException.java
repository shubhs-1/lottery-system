package com.assignment.lotterysystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class SaveFailureException extends Exception {
    public SaveFailureException(String message) {
        super(message);
    }
}

