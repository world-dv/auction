package com.tasksprints.auction.domain.bid.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class BidRequest {
    private Long userId;
    private Long auctionId;
    private BigDecimal amount;
}
