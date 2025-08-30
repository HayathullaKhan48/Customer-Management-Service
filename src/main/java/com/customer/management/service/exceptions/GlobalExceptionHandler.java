package com.customer.management.service.exception;

import com.customer.management.service.exceptions.CustomerAlreadyExistsException;
import com.customer.management.service.exceptions.CustomerNotFoundException;
import com.customer.management.service.exceptions.InvalidOtpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleExists(CustomerAlreadyExistsException ex) {
        Map<String,String> m = new HashMap<>();
        m.put("error", ex.getMessage());
        return new ResponseEntity<>(m, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleNotFound(CustomerNotFoundException ex) {
        Map<String,String> m = new HashMap<>();
        m.put("error", ex.getMessage());
        return new ResponseEntity<>(m, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<Map<String,String>> handleOtp(InvalidOtpException ex) {
        Map<String,String> m = new HashMap<>();
        m.put("error", ex.getMessage());
        return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleAll(Exception ex) {
        Map<String,String> m = new HashMap<>();
        m.put("error", ex.getMessage());
        return new ResponseEntity<>(m, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
