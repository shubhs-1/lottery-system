package com.assignment.lotterysystem.exception;

import com.assignment.lotterysystem.dto.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleDataNotFoundException(final DataNotFoundException exception) {
        RestResponse.Message failureMessage = RestResponse.Message.build(exception.getClass(), exception.getMessage());

        RestResponse response = RestResponse.build(RestResponse.EMPY_JSON_OBJECT,
                RestResponse.RESPONSE_SUCCESS, Arrays.asList(failureMessage));

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LotteryStatusException.class)
    public ResponseEntity<Object> handleLotteryStatusException(final LotteryStatusException exception) {
        RestResponse.Message failureMessage = RestResponse.Message.build(exception.getClass(), exception.getMessage());

        RestResponse response = RestResponse.build(RestResponse.EMPY_JSON_OBJECT,
                RestResponse.RESPONSE_SUCCESS, Arrays.asList(failureMessage));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(SaveFailureException.class)
    public ResponseEntity<Object> handleSaveFailureException(final SaveFailureException exception) {
        RestResponse.Message failureMessage = RestResponse.Message.build(exception.getClass(), exception.getMessage());

        RestResponse response = RestResponse.build(RestResponse.EMPY_JSON_OBJECT,
                RestResponse.RESPONSE_FAILURE, Arrays.asList(failureMessage));

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LotterySubmissionFailed.class)
    public ResponseEntity<Object> handleLotterySubmissionException(final LotterySubmissionFailed exception) {
        RestResponse.Message failureMessage = RestResponse.Message.build(exception.getClass(), exception.getMessage());

        RestResponse response = RestResponse.build(RestResponse.EMPY_JSON_OBJECT,
                RestResponse.RESPONSE_FAILURE, Arrays.asList(failureMessage));

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(final UserAlreadyExistsException exception) {
        RestResponse.Message failureMessage = RestResponse.Message.build(exception.getClass(), exception.getMessage());

        RestResponse response = RestResponse.build(RestResponse.EMPY_JSON_OBJECT,
                RestResponse.RESPONSE_SUCCESS, Arrays.asList(failureMessage));

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleRuntimeException(final RuntimeException exception) {
        RestResponse.Message failureMessage = RestResponse.Message.build(exception.getClass(), exception.getMessage());

        RestResponse response = RestResponse.build(RestResponse.EMPY_JSON_OBJECT,
                RestResponse.RESPONSE_FAILURE, Arrays.asList(failureMessage));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
