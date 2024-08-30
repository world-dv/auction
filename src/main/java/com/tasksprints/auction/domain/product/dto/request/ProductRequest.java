package com.tasksprints.auction.domain.product.dto.request;

import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductRequest {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Register{
        private String name;
        private String description;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        private Long productId;
        private String name;
        private String description;
    }

}
