package com.msciq.storage.exception;

import com.msciq.storage.common.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdvise extends ResponseEntityExceptionHandler {

    /**
     * The handleMethodArgumentNotValid handles the MethodArgumentNotValidException
     *      which is thrown when validation on an argument annotated with @Valid fails.
     * @param exception the exception to handle
     * @param headers the headers to be written to the response
     * @param status the status code to use for the response
     * @param request the incoming request
     *
     * @return
     *      a ResponseEntity for the response to use
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException exception, HttpHeaders headers,
                HttpStatus status, WebRequest request) {

        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("timestamp", LocalDate.now());
        errorResponse.put("status", status.value());

        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValueIsNotAlphaNumericException.class)
    public ResponseEntity<Object> handleValueIsNotAlphaNumericException(
            ValueIsNotAlphaNumericException ex, WebRequest request) {

        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StringLengthExceedsTheLimitException.class)
    public ResponseEntity<Object> handleStringLengthExceedsTheLimitException(
            StringLengthExceedsTheLimitException ex, WebRequest request) {

        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAStringException.class)
    public ResponseEntity<Object> handleNotAStringException(
            StringLengthExceedsTheLimitException ex, WebRequest request) {

        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleServerException(
            Exception ex, WebRequest request) {

        if(ex.getMessage().equalsIgnoreCase("Access is denied")){
            return buildErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
        }else{
            return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    private ResponseEntity<Object> buildErrorResponse(String errorMessage, HttpStatus httpStatus) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(Constants.MESSAGE, errorMessage);
        body.put(Constants.STATUS, httpStatus);
        body.put(Constants.TIMESTAMP, LocalDateTime.now(ZoneOffset.UTC));

        return new ResponseEntity<>(body, httpStatus);
    }


}
