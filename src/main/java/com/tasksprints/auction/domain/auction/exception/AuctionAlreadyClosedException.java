package com.tasksprints.auction.domain.auction.exception;

public class AuctionAlreadyClosedException extends RuntimeException {
    public AuctionAlreadyClosedException(String message) {
        super(message);
    }
}
