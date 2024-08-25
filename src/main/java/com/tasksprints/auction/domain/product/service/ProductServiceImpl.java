package com.tasksprints.auction.domain.product.service;

import com.tasksprints.auction.domain.auction.exception.AuctionNotFoundException;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.repository.AuctionRepository;
import com.tasksprints.auction.domain.product.dto.response.ProductResponse;
import com.tasksprints.auction.domain.product.dto.request.ProductRequest;
import com.tasksprints.auction.domain.product.exception.ProductNotFoundException;
import com.tasksprints.auction.domain.product.model.Product;
import com.tasksprints.auction.domain.product.repository.ProductImageRepository;
import com.tasksprints.auction.domain.product.repository.ProductRepository;
import com.tasksprints.auction.domain.user.exception.UserNotFoundException;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements  ProductService{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public String uploadImage() {
        /**
         * S3 버킷으로 올리곱 link 반환
         */
        String url = "url";
        return url;
    }

    @Override
    public List<String> uploadImageBulk() {
        /**
        * S3 버킷으로 올리곱 link 반환
        */
        List<String> urlList =  List.of("url1","url2","url3");

        return urlList;
    }



    @Override
    public void delete(Long ProductId) {
        /** 완전 삭제할건지 아닌지 판단
         */
    }

    @Override
    @Deprecated
    public List<ProductResponse> getProductsByUserId(Long userId) {
        List<Product> products = productRepository.findAllByUserId(userId);
        return convertToDTOList(products);
    }

    @Override
    public ProductResponse getProductByAuctionId(Long auctionId) {
        Product product = productRepository.findByAuctionId(auctionId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return ProductResponse.of(product);
    }

    @Override
    public ProductResponse register(Long userId, Long auctionId, ProductRequest.Register request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException("Auction not found"));

        Product newProduct = Product.create(
                request.getName(),
                request.getDescription(),
                user,
                auction
        );

        Product createdProduct = productRepository.save(newProduct);
        // 이미지 추가 로직 필요


        return ProductResponse.of(createdProduct);
    }

    @Override
    public ProductResponse update(ProductRequest.Update request) {
        Long productId = request.getProductId();
        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        foundProduct.update(request.getName(), request.getDescription());
        Product savedProduct = productRepository.save(foundProduct);
        return ProductResponse.of(savedProduct);
    }

    private List<ProductResponse> convertToDTOList(List<Product> products) {
        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
