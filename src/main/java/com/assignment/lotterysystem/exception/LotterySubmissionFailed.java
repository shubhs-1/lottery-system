package com.assignment.lotterysystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class LotterySubmissionFailed extends Exception {
    public LotterySubmissionFailed(String message) {
        super(message);
    }

}