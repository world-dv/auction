package com.tasksprints.auction.domain.auction.dto.request;

import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import com.tasksprints.auction.domain.product.model.ProductCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AuctionRequest {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        LocalDateTime startTime;
        LocalDateTime endTime;
        BigDecimal startingBid;
        AuctionCategory auctionCategory;
        AuctionStatus auctionStatus;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuctionCategoryParam {
        AuctionCategory auctionCategory;

        public AuctionCategoryParam(String auctionCategory) {
            this.auctionCategory = AuctionCategory.fromDisplayName(auctionCategory);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductCategoryParam {
        ProductCategory productCategory;

        public ProductCategoryParam(String productCategory) {
            this.productCategory = ProductCategory.fromDisplayName(productCategory);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuctionStatusParam {
        AuctionStatus auctionStatus;

        public AuctionStatusParam(String auctionStatus) {
            this.auctionStatus = AuctionStatus.fromDisplayName(auctionStatus);
        }

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SearchCondition {
        AuctionCategory auctionCategory;
        ProductCategory productCategory;
        LocalDateTime startTime;
        LocalDateTime endTime;
        BigDecimal minPrice;
        BigDecimal maxPrice;
        AuctionStatus auctionStatus;
    }
}
