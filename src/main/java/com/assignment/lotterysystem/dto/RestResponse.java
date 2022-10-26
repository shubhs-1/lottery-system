package com.assignment.lotterysystem.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;

/**
 * @author Shubham Kalaria
 * POJO class to centralise REST APIs response structure
 */
@Builder
@Data
@JsonDeserialize( builder = RestResponse.RestResponseBuilder.class )
public class RestResponse implements Serializable {
    public static final boolean RESPONSE_FAILURE = false;

    public static final boolean RESPONSE_SUCCESS = true;

    public static final String SUCCESS_STATUS = "success";

    public static final String FAILURE_STATUS = "failure";

    public static final EmptyJsonResponse EMPY_JSON_OBJECT = new EmptyJsonResponse();

    private static final String MSG_OPERATION_SUCCEEDED = "Operation succeeded!";

    /**
     * Represents success model.
     */
    private Object data;

    /**
     * Represents Success or Failure
     */
    private Boolean success;

    /**
     * List of messages; Aggregator or Service Delegate can prepare capture
     * messages at different stages.
     */
    private List<Message> messages;

    @Builder
    @Getter
    @JsonDeserialize( builder = Message.MessageBuilder.class )
    public static class Message {
        /**
         * Exception name.
         */
        private String exception;

        /**
         * Detailed information about success or failure state.
         */
        private String message;

        /**
         * State of each operation
         */
        private String status;

        /**
         * Requested URI. Aggregator or service layer can capture different URIs
         * contacted
         */
        private String requestedUri;

        /**
         * Timestamp in string format.
         */
        private String timestamp;

        /**
         * Convert the given time to readable format.
         */
        private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder().appendPattern( "yyyy-MM-dd HH:mm:ss" ).toFormatter();

        /**
         * Message builder with message and status
         */
        public static Message build( final Class exceptionClass, final String message ) {
            return Message.builder().exception(exceptionClass.getName()).message( message ).status( FAILURE_STATUS ).requestedUri( extractRequestedUri() ).timestamp( LocalDateTime.now().format( DATE_TIME_FORMATTER ) ).build();

        }

        /**
         * Message builder with message and status
         */
        public static Message build( final String message, final String status ) {
            return Message.builder().message( message ).status( status ).requestedUri( extractRequestedUri() ).timestamp( LocalDateTime.now().format( DATE_TIME_FORMATTER ) ).build();

        }

        /**
         * Message builder with message, status and requestURI.
         */
        public static Message build( final String message, final String status, final String requestURI ) {
            return Message.builder().message( message ).status( status ).requestedUri( requestURI ).timestamp( LocalDateTime.now().format( DATE_TIME_FORMATTER ) ).build();

        }

        /**
         * Message builder with message, status and requestURI.
         */
        public static Message build(final Class exceptionClass, final String message, final String status, final String requestURI ) {
            return Message.builder().exception(exceptionClass.getName()).message( message ).status( status ).requestedUri( requestURI ).timestamp( LocalDateTime.now().format( DATE_TIME_FORMATTER ) ).build();

        }


        private static String extractRequestedUri() {

            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if ( requestAttributes instanceof ServletRequestAttributes) {
                HttpServletRequest request = ( (ServletRequestAttributes) requestAttributes ).getRequest();
                return request.getRequestURI();
            }

            return Strings.EMPTY;
        }
    }

    /**
     * Success ResponseBuilder with data.
     */
    public static RestResponse successBuild( final Object data ) {
        return RestResponse.builder().data( data ).success( RESPONSE_SUCCESS ).messages( Arrays.asList( Message.build( MSG_OPERATION_SUCCEEDED, SUCCESS_STATUS ) ) ).build();
    }

    /**
     * Success ResponseBuilder with data and message.
     */
    public static RestResponse successBuild( final Object data, final String message ) {
        return RestResponse.builder().data( data ).success( RESPONSE_SUCCESS ).messages( Arrays.asList( Message.build( message, SUCCESS_STATUS ) ) ).build();
    }

    /**
     * Success ResponseBuilder with message.
     */
    public static RestResponse successBuild( final String message ) {
        return RestResponse.builder().data( EMPY_JSON_OBJECT ).success( RESPONSE_SUCCESS ).messages( Arrays.asList( Message.build( message, SUCCESS_STATUS ) ) ).build();
    }

    /**
     * Failure ResponseBuilder with data.
     */
    public static RestResponse failureBuild( final Class exceptionClass, final String message ) {
        return RestResponse.builder().data( EMPY_JSON_OBJECT ).success( RESPONSE_FAILURE ).messages( Arrays.asList( Message.build( exceptionClass, message ) ) ).build();
    }

    /**
     * Failure ResponseBuilder with data.
     */
    public static RestResponse failureBuild( final String message ) {
        return RestResponse.builder().data( EMPY_JSON_OBJECT ).success( RESPONSE_FAILURE ).messages( Arrays.asList( Message.build( message, FAILURE_STATUS ) ) ).build();
    }

    /**
     * ResponseBuilder with data, success flag and List of Messages.
     */
    public static RestResponse build( final Object data, final boolean success, final List<Message> messages ) {
        return RestResponse.builder().data( data ).success( success ).messages( messages ).build();
    }

    @JsonDeserialize
    @JsonSerialize
    public static class EmptyJsonResponse {}
}
