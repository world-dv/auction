package com.tasksprints.auction.domain.product.service;

import com.tasksprints.auction.domain.product.dto.ProductDTO;
import com.tasksprints.auction.domain.product.dto.ProductRequest;

import java.util.List;

/**
 * item으로 Auction 검색
 */
public interface ProductService {
    void uploadImage();
    void uploadImageBulk();
    List<ProductDTO> getProductsByUserId(Long userId);
    ProductDTO getProductByAuctionId(Long auctionId);

    ProductDTO register(Long userId, Long auctionId, ProductRequest.Register product);

    void delete(Long ProductId);

    ProductDTO update(ProductRequest.Update product);
}
