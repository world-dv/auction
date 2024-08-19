package com.tasksprints.auction.domain.product.dto;

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
}
