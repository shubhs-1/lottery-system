package com.assignment.lotterysystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class LotteryStatusException extends Exception {
    public LotteryStatusException(String message) {
        super(message);
    }
}
