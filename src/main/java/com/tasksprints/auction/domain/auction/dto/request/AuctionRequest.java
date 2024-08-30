package com.tasksprints.auction.domain.auction.dto.request;

import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AuctionRequest {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create{
        LocalDateTime startTime;
        LocalDateTime endTime;
        BigDecimal startingBid;
        AuctionCategory auctionCategory;
        AuctionStatus auctionStatus;
    }

}
