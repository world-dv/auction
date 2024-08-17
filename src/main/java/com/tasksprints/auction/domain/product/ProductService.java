package com.tasksprints.auction.domain.product;

import java.util.List;

/**
 * item으로 Auction 검색
 */
public interface ProductService {
    List<ProductDTO> getProductsByUserId(Long userId);
    ProductDTO getProductByAuctionId(Long auctionId);

    ProductDTO register(Long userId, Long auctionId, ProductRequest.Register product);

    void delete(Long ProductId);

    ProductDTO update();
}
