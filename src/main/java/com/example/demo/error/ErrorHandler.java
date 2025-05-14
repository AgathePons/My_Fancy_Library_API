package com.example.demo.error;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoDataFoundError.class)
    public ResponseEntity<ErrorMessage> handleNoDataFoundError(Exception exception, WebRequest webRequest) {
        return responseEntity(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(BadRequestError.class)
    public ResponseEntity<ErrorMessage> handleBadRequestError(Exception exception, WebRequest webRequest) {
        return responseEntity(HttpStatus.BAD_REQUEST, exception);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode statusCode,
                                                                  WebRequest request) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ErrorMessage errorMessage = ErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private static ResponseEntity<ErrorMessage> responseEntity(HttpStatus httpStatus, Exception exception) {
        return ResponseEntity.status(httpStatus).body(
                ErrorMessage.builder()
                        .status(httpStatus.value())
                        .message(exception.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
