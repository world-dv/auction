package com.tasksprints.auction.domain.bid.exception;

public class BidNotFoundException extends RuntimeException {
    public BidNotFoundException(String message) {
        super(message);
    }
}
