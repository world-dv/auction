package com.tasksprints.auction.domain.product.dto.response;

import com.tasksprints.auction.domain.product.model.Product;
import com.tasksprints.auction.domain.product.model.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<String> productImageList;

    private static List<String> extractProductImageList(Product product){
        return product.getProductImageList()
                .stream()
                .map(ProductImage::getImageUrl)
                .toList();
    }
    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .ownerId(product.getOwner().getId())
                .ownerNickName(product.getOwner().getNickName())
                .auctionId(product.getAuction().getId())
                .productImageList(extractProductImageList(product))
                .build();
    }
}
