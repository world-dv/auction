package com.tasksprints.auction.common.initializer;

import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import com.tasksprints.auction.domain.auction.repository.AuctionRepository;
import com.tasksprints.auction.domain.product.model.Product;
import com.tasksprints.auction.domain.product.model.ProductImage;
import com.tasksprints.auction.domain.product.repository.ProductImageRepository;
import com.tasksprints.auction.domain.product.repository.ProductRepository;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class AuctionInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public AuctionInitializer(UserRepository userRepository, AuctionRepository auctionRepository, ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    private void createDummyUser() {
        User user1 = User.create("name", "email@email.com", "password", "NickName");
        userRepository.save(user1);
    }

    private Auction createDummyAuction(User user) {
        Auction auction = Auction.create(LocalDateTime.now(), LocalDateTime.now().plusHours(2), BigDecimal.TEN, AuctionCategory.PRIVATE_FREE, AuctionStatus.ACTIVE, user);
        return auctionRepository.save(auction);
    }

    private void createDummyProduct(User user, Auction auction) {
        ProductImage productImage = ProductImage.create("/url");
        // Save the productImage to avoid the TransientObjectException
        productImageRepository.save(productImage);

        Product product = Product.create("name", "description", user, auction, "헤어", List.of(productImage));
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        User user = userRepository.save(User.create("name", "email@email.com", "password", "NickName"));

        // 각 제품에 대해 새로운 경매를 생성
        for (int i = 0; i < 100; i++) {
            Auction auction = createDummyAuction(user);
            createDummyProduct(user, auction);
        }
    }
}
