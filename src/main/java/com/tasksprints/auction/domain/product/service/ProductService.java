package com.tasksprints.auction.domain.product.service;

import com.tasksprints.auction.domain.product.dto.response.ProductResponse;
import com.tasksprints.auction.domain.product.dto.request.ProductRequest;

import java.util.List;

/**
 * item으로 Auction 검색
 */
public interface ProductService {
    String uploadImage();
    List<String> uploadImageBulk();
    List<ProductResponse> getProductsByUserId(Long userId);
    ProductResponse getProductByAuctionId(Long auctionId);

    ProductResponse register(Long userId, Long auctionId, ProductRequest.Register product);

    void delete(Long ProductId);

    ProductResponse update(ProductRequest.Update product);
}
