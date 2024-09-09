package com.tasksprints.auction.domain.auction.model;

public enum AuctionCategory {
    PRIVATE_FREE,
    PUBLIC_FREE,
    PRIVATE_PAID,
    PUBLIC_PAID;

    public static AuctionCategory fromDisplayName(String auctionCategory) {
        try {
            return AuctionCategory.valueOf(auctionCategory.toUpperCase()); // 대문자로 변환하여 비교
        } catch (IllegalArgumentException e) {
            return PUBLIC_FREE; // 유효하지 않은 값일 경우 기본값으로 PUBLIC_FREE 반환
        }
    }
}
