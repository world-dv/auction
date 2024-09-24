package com.tasksprints.auction.domain.auction.model;

public enum AuctionCategory {
    PRIVATE_FREE,
    PUBLIC_FREE,
    PRIVATE_PAID,
    PUBLIC_PAID;


    public static AuctionCategory fromDisplayName(String auctionCategory) {
        for (AuctionCategory category : values()) {
            if (category.name().equalsIgnoreCase(auctionCategory)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown auctionCategory: " + auctionCategory);
    }
}
