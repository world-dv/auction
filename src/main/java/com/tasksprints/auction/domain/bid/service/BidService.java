package com.tasksprints.auction.domain.bid.service;

import com.tasksprints.auction.domain.bid.dto.BidDTO;

import java.math.BigDecimal;

public interface BidService {
    /**
     * 입찰
     * 입찰금액 변경
     */
    BidDTO submitBid(Long userId, Long auctionId, BigDecimal amount);

    BidDTO updateBidAmount(Long userId, Long auctionId, BigDecimal amount);

    Boolean hasUserAlreadyBid(Long auctionId);
}
