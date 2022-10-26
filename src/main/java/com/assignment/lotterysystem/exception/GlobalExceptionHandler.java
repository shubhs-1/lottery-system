package com.assignment.lotterysystem.exception;

import com.assignment.lotterysystem.dto.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

/**
 * @author Shubham Kalaria
 * Centralised exception handler to handle possible exceptions during application execution
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handler to initialize and create structure for DataNotFoundException
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleDataNotFoundException(final DataNotFoundException exception) {
        RestResponse.Message failureMessage = RestResponse.Message.build(exception.getClass(), exception.getMessage());

        RestResponse response = RestResponse.build(RestResponse.EMPY_JSON_OBJECT,
                RestResponse.RESPONSE_SUCCESS, Arrays.asList(failureMessage));

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handler to initialize and create structure for LotteryStatusException
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(LotteryStatusException.class)
    public ResponseEntity<Object> handleLotteryStatusException(final LotteryStatusException exception) {
        RestResponse.Message failureMessage = RestResponse.Message.build(exception.getClass(), exception.getMessage());

        RestResponse response = RestResponse.build(RestResponse.EMPY_JSON_OBJECT,
                RestResponse.RESPONSE_SUCCESS, Arrays.asList(failureMessage));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handler to initialize and create structure for SaveFailureException
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(SaveFailureException.class)
    public ResponseEntity<Object> handleSaveFailureException(final SaveFailureException exception) {
        RestResponse.Message failureMessage = RestResponse.Message.build(exception.getClass(), exception.getMessage());

        RestResponse response = RestResponse.build(RestResponse.EMPY_JSON_OBJECT,
                RestResponse.RESPONSE_FAILURE, Arrays.asList(failureMessage));

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handler to initialize and create structure for LotterySubmissionFailed
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(LotterySubmissionFailed.class)
    public ResponseEntity<Object> handleLotterySubmissionException(final LotterySubmissionFailed exception) {
        RestResponse.Message failureMessage = RestResponse.Message.build(exception.getClass(), exception.getMessage());

        RestResponse response = RestResponse.build(RestResponse.EMPY_JSON_OBJECT,
                RestResponse.RESPONSE_FAILURE, Arrays.asList(failureMessage));

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handler to initialize and create structure for UserAlreadyExistsException
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(final UserAlreadyExistsException exception) {
        RestResponse.Message failureMessage = RestResponse.Message.build(exception.getClass(), exception.getMessage());

        RestResponse response = RestResponse.build(RestResponse.EMPY_JSON_OBJECT,
                RestResponse.RESPONSE_SUCCESS, Arrays.asList(failureMessage));

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handler to initialize and create structure for generic Exception
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleRuntimeException(final RuntimeException exception) {
        RestResponse.Message failureMessage = RestResponse.Message.build(exception.getClass(), exception.getMessage());

        RestResponse response = RestResponse.build(RestResponse.EMPY_JSON_OBJECT,
                RestResponse.RESPONSE_FAILURE, Arrays.asList(failureMessage));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
