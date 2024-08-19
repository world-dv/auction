package com.tasksprints.auction.domain.auction.exception;

public class InvalidAuctionTimeException extends RuntimeException {
    public InvalidAuctionTimeException(String message) {
        super(message);
    }
}
