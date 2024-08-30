package com.tasksprints.auction.domain.auction.dto.response;

import com.tasksprints.auction.domain.auction.model.Auction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String category;
    private String status;
    private BigDecimal startingBid;

    private Long sellerId;
    private String sellerNickName;


    public static AuctionResponse of(Auction auction){
        return AuctionResponse.builder()
                .id(auction.getId())
                .startTime(auction.getStartTime())
                .endTime(auction.getEndTime())
                .category(auction.getAuctionCategory().name())
                .status(auction.getAuctionStatus().name())
                .startingBid(auction.getStartingBid())
                .sellerId(auction.getSeller().getId())
                .sellerNickName(auction.getSeller().getNickName())
                .build();
    }



}
