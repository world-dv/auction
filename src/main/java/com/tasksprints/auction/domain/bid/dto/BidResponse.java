package com.tasksprints.auction.domain.bid.dto;

import com.tasksprints.auction.domain.bid.model.Bid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BidResponse {
    Long userId;
    String name;
    Long auctionId;
    BigDecimal amount;

    public static BidResponse of(Bid bid){
        return BidResponse.builder()
                .userId(bid.getUser().getId())
                .name(bid.getUser().getName())
                .auctionId(bid.getAuction().getId())
                .amount(bid.getAmount())
                .build();
        /** 아이템 목록 추가**/
    }
}
