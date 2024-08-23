package com.tasksprints.auction.common.handler;

import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.common.response.ApiResult;
import com.tasksprints.auction.domain.auction.exception.AuctionAlreadyClosedException;
import com.tasksprints.auction.domain.auction.exception.AuctionEndedException;
import com.tasksprints.auction.domain.auction.exception.AuctionNotFoundException;
import com.tasksprints.auction.domain.auction.exception.InvalidAuctionTimeException;
import com.tasksprints.auction.domain.bid.exception.BidNotFoundException;
import com.tasksprints.auction.domain.bid.exception.InvalidBidAmountException;
import com.tasksprints.auction.domain.product.exception.ProductNotFoundException;
import com.tasksprints.auction.domain.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResult<String>> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResult.failure(ApiResponseMessages.USER_NOT_FOUND));
    }

    @ExceptionHandler(AuctionNotFoundException.class)
    public ResponseEntity<ApiResult<String>> handleAuctionNotFoundException(AuctionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResult.failure(ApiResponseMessages.AUCTION_NOT_FOUND));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResult<String>> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResult.failure(ApiResponseMessages.PRODUCT_NOT_FOUND));
    }

    @ExceptionHandler(BidNotFoundException.class)
    public ResponseEntity<ApiResult<String>> handleBidNotFoundException(BidNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResult.failure(ApiResponseMessages.BID_NOT_FOUND));
    }

    @ExceptionHandler(InvalidAuctionTimeException.class)
    public ResponseEntity<ApiResult<String>> handleInvalidAuctionTimeException(InvalidAuctionTimeException ex) {
        String message = "Invalid auction time: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResult.failure(message));
    }
    @ExceptionHandler(InvalidBidAmountException.class)
    public ResponseEntity<ApiResult<String>> handleInvalidBidAmountException(InvalidBidAmountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResult.failure(ex.getMessage()));
    }

    @ExceptionHandler(AuctionAlreadyClosedException.class)
    public ResponseEntity<ApiResult<String>> handleAuctionAlreadyClosedException(AuctionAlreadyClosedException ex) {
        String message = "Auction is already closed";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResult.failure(message));
    }

    @ExceptionHandler(AuctionEndedException.class)
    public ResponseEntity<ApiResult<String>> handleAuctionEndedException(AuctionEndedException ex) {
        String message = "This auction has already ended.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResult.failure(message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResult<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResult.failure(ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResult<String>> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResult.failure(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResult<String>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResult.failure(ex.getMessage()));
    }
}
