package com.tasksprints.auction.common.handler;

import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.common.response.ApiResponse;
import com.tasksprints.auction.domain.auction.exception.AuctionAlreadyClosedException;
import com.tasksprints.auction.domain.auction.exception.AuctionNotFoundException;
import com.tasksprints.auction.domain.auction.exception.InvalidAuctionTimeException;
import com.tasksprints.auction.domain.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure(ApiResponseMessages.USER_NOT_FOUND));
    }

    @ExceptionHandler(AuctionNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleAuctionNotFoundException(AuctionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure(ApiResponseMessages.AUCTION_NOT_FOUND));
    }

    @ExceptionHandler(InvalidAuctionTimeException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidAuctionTimeException(InvalidAuctionTimeException ex) {
        String message = "Invalid auction time: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(message));
    }

    @ExceptionHandler(AuctionAlreadyClosedException.class)
    public ResponseEntity<ApiResponse<String>> handleAuctionAlreadyClosedException(AuctionAlreadyClosedException ex) {
        String message = "Auction is already closed";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.failure(ex.getMessage()));
    }
}
