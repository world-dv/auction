package com.tasksprints.auction.common;
import com.tasksprints.auction.domain.user.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleUserNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(500).body(ApiResponse.failure(ex.getMessage()));
    }
}
