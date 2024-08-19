package com.tasksprints.auction.domain.product.service;

import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.repository.AuctionRepository;
import com.tasksprints.auction.domain.product.dto.ProductDTO;
import com.tasksprints.auction.domain.product.dto.ProductRequest;
import com.tasksprints.auction.domain.product.model.Product;
import com.tasksprints.auction.domain.product.repository.ProductRepository;
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
    @Override
    public List<ProductDTO> getProductsByUserId(Long userId) {
        List<Product> product = productRepository.findAllByUserId(userId);
        return product.stream()
                .map(ProductDTO::of)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductByAuctionId(Long auctionId) {
        Product product = productRepository.findByAuctionId(auctionId)
                .orElseThrow();
        return ProductDTO.of(product);
    }

    @Override
    public ProductDTO register(Long userId, Long auctionId, ProductRequest.Register product) {
        User user = userRepository.findById(userId).orElseThrow();
        Auction auction = auctionRepository.findById(auctionId).orElseThrow();

        Product newProduct = Product.create(
                product.getName(),
                product.getDescription(),
                user,
                auction);

        Product createdProduct = productRepository.save(newProduct);
        /** 이미지 추가 로직 필요**/

        return ProductDTO.of(createdProduct);
    }

    @Override
    public void delete(Long ProductId) {
        /** 완전 삭제할건지 아닌지 판단
         */
    }

    @Override
    public ProductDTO update(ProductRequest.Update product){
        Long productId = product.getProductId();
        Product foundProduct = productRepository.findById(productId)
                .orElseThrow();
        foundProduct.update(product.getName(), product.getDescription());
        Product savedProduct = productRepository.save(foundProduct);
        return ProductDTO.of(savedProduct);
    }
}
