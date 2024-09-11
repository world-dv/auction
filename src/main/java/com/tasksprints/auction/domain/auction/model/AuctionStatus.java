package com.tasksprints.auction.domain.auction.model;

public enum AuctionStatus {
    PENDING,
    ACTIVE,
    CLOSED,
    CANCELED;

    public static AuctionStatus fromDisplayName(String auctionStatus) {
        try {
            return AuctionStatus.valueOf(auctionStatus.toUpperCase()); // 대문자로 변환하여 비교
        } catch (IllegalArgumentException e) {
            return AuctionStatus.ACTIVE; // 유효하지 않은 값일 경우 기본값으로 ACTIVE 반환
        }
    }
}
