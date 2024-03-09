package com.spring.angular.ecommerce.handlers;

import com.spring.angular.ecommerce.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(IllegalArgumentException ex) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .source(ex.getClass().getName())
            .message("Resource Not Found : " + ex.getMessage())
            .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ValidationException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .source(ex.getClass().getName())
            .message("Invalid resource : " + ex.getMessage())
            .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .source(ex.getClass().getName())
            .message("Internal Server Error : " + ex.getMessage())
            .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
