package com.tasksprints.auction.domain.product.dto.response;

import com.tasksprints.auction.domain.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductResponse {
    private Long productId;
    private String name;
    private String description;
    private Long ownerId;
    private String ownerNickName;

    private Long auctionId;

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .ownerId(product.getOwner().getId())
                .ownerNickName(product.getOwner().getNickName())
                .auctionId(product.getAuction().getId())
                .build();
    }
}
