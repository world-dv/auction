package com.tasksprints.auction.domain.product;

import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.repository.AuctionRepository;
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

        return ProductDTO.of(createdProduct);
    }

    @Override
    public void delete(Long ProductId) {
    }

    @Override
    public ProductDTO update() {
        return null;
    }
}
