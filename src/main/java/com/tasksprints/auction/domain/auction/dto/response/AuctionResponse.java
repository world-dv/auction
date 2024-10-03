package com.tasksprints.auction.domain.auction.dto.response;

import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.product.model.ProductImage;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
    private Long viewCount;
    private Long sellerId;
    private String sellerNickName;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Details {
        private Long id;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String category;
        private String status;
        private BigDecimal startingBid;
        private Long viewCount;
        private Long sellerId;
        private String sellerNickName;
        private Long productId;
        private String productCategory;
        @Setter
        private List<String> productImageUrls;

        public static AuctionResponse.Details of(Auction auction) {
            return AuctionResponse.Details.builder()
                .id(auction.getId())
                .startTime(auction.getStartTime())
                .endTime(auction.getEndTime())
                .category(auction.getAuctionCategory().name())
                .status(auction.getAuctionStatus().name())
                .startingBid(auction.getStartingBid())
                .viewCount(auction.getViewCount())
                .sellerId(auction.getSeller() != null ? auction.getSeller().getId() : null)
                .sellerNickName(auction.getSeller() != null ? auction.getSeller().getNickName() : null)
                .productId(auction.getProduct() != null ? auction.getProduct().getId() : null)
                .productCategory(auction.getProduct() != null && auction.getProduct().getCategory() != null ? auction.getProduct().getCategory().name() : null)
                .productImageUrls(auction.getProduct() != null && auction.getProduct().getProductImageList() != null
                    ? auction.getProduct().getProductImageList().stream()
                    .map(ProductImage::getImageUrl)
                    .collect(toList())
                    : Collections.emptyList())
                .build();
        }
    }
    public static AuctionResponse of(Auction auction) {
        return AuctionResponse.builder()
            .id(auction.getId())
            .startTime(auction.getStartTime())
            .endTime(auction.getEndTime())
            .category(auction.getAuctionCategory().name())
            .status(auction.getAuctionStatus().name())
            .startingBid(auction.getStartingBid())
            .viewCount(auction.getViewCount())
            .sellerId(auction.getSeller().getId())
            .sellerNickName(auction.getSeller().getNickName())
            .build();
    }


}
