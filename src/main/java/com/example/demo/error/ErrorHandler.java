package com.example.demo.error;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

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
