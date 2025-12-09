package com.clon.etoro.infraestructure.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

//    @ExceptionHandler(CountryNotActiveException.class)
//    public ResponseEntity<?> handleCountryNotActive(CountryNotActiveException ex){
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
//                "timestamp", LocalDateTime.now(),
//                "error", "COUNTRY_NOT_ACTIVE",
//                "message", ex.getMessage()
//        ));
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "error", "BAD_REQUEST",
                "message", ex.getMessage()
        ));
    }
}

