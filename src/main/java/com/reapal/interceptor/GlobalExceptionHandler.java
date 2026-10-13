package com.reapal.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/** Returns a real HTTP error instead of rendering a missing legacy error template. */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> defaultErrorHandler(Exception exception) {
        var status = exception instanceof ErrorResponse error
                ? error.getStatusCode() : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(Map.of(
                "code", status.value(), "msg", status.value() == 404 ? "Not found" : "Request failed"));
    }
}
