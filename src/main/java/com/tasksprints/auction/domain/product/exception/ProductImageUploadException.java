package com.tasksprints.auction.domain.product.exception;


public class ProductImageUploadException extends RuntimeException {

    public ProductImageUploadException(String message) {
        super(message);
    }

    public ProductImageUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}