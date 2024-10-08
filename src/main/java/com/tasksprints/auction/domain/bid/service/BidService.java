package com.tasksprints.auction.domain.bid.service;

import com.tasksprints.auction.domain.bid.dto.BidResponse;

import java.math.BigDecimal;

public interface BidService {
    /**
     * 입찰
     * 입찰금액 변경
     */
    BidResponse submitBid(Long userId, Long auctionId, BigDecimal amount);

    BidResponse updateBidAmount(Long userId, Long auctionId, BigDecimal amount);

    Boolean hasUserAlreadyBid(Long auctionId);

    BidResponse getBidByUuid(String uuid);
}
