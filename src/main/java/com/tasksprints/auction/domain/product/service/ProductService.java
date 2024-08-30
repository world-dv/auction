package com.tasksprints.auction.domain.product.service;

import com.tasksprints.auction.domain.product.dto.response.ProductResponse;
import com.tasksprints.auction.domain.product.dto.request.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * item으로 Auction 검색
 */
public interface ProductService {
    String uploadImage(MultipartFile image) throws IOException;
    List<String> uploadImageBulk(List<MultipartFile> images);
    List<ProductResponse> getProductsByUserId(Long userId);
    ProductResponse getProductByAuctionId(Long auctionId);

    ProductResponse register(Long userId, Long auctionId, ProductRequest.Register product, List<MultipartFile> images);

    void delete(Long ProductId);

    ProductResponse update(ProductRequest.Update product);
}
