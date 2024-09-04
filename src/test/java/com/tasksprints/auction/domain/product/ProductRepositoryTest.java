package com.tasksprints.auction.domain.product;

import com.tasksprints.auction.common.config.QueryDslConfig;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import com.tasksprints.auction.domain.auction.repository.AuctionRepository;
import com.tasksprints.auction.domain.product.model.Product;
import com.tasksprints.auction.domain.product.repository.ProductRepository;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(QueryDslConfig.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    private User owner;
    private Auction auction;

    @BeforeEach
    public void setUp() {
        owner = createUser("testUser", "test", "test@naver.com");
        auction = createAuction(owner, BigDecimal.ONE, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Product product = createProduct("sample product1", "description", owner, auction);
        productRepository.save(product);
    }

    @Test
    @DisplayName("Find product by auction ID")
    public void testFindByAuctionId() {
        Optional<Product> foundProduct = productRepository.findByAuctionId(auction.getId());

        assertTrue(foundProduct.isPresent());
        assertThat(foundProduct.get().getAuction().getId()).isEqualTo(auction.getId());
    }

    @Test
    @DisplayName("Find all products by user ID")
    public void testFindAllByUserId() {
        List<Product> products = productRepository.findByOwnerId(owner.getId());

        assertThat(products).isNotEmpty();
        assertThat(products.get(0).getOwner().getId()).isEqualTo(owner.getId());
    }

    @Test
    @DisplayName("Save a new product")
    public void testSaveProduct() {
        Auction auction2 = createAuction(owner, BigDecimal.ONE, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Product newProduct = createProduct("Another Product", "this is another product", owner, auction2);

        Product savedProduct = productRepository.save(newProduct);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Another Product");
    }

    @Test
    @DisplayName("Find product by ID")
    public void testFindById() {
        Product product = productRepository.findAll().get(0); // Use first product for testing
        Optional<Product> foundProduct = productRepository.findById(product.getId());

        assertTrue(foundProduct.isPresent());
        assertThat(foundProduct.get().getId()).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("Delete product by ID")
    public void testDeleteById() {
        Product product = productRepository.findAll().get(0); // Use first product for testing
        productRepository.deleteById(product.getId());

        Optional<Product> deletedProduct = productRepository.findById(product.getId());
        assertTrue(deletedProduct.isEmpty());
    }

    // Helper methods to minimize code duplication
    private User createUser(String name, String nickName, String email) {
        User user = User.builder()
                .name(name)
                .nickName(nickName)
                .password("password")  // Use a consistent password for all users
                .email(email)
                .build();
        return userRepository.save(user);
    }

    private Auction createAuction(User owner, BigDecimal startingBid, LocalDateTime startTime, LocalDateTime endTime) {
        Auction auction = Auction.builder()
                .startingBid(startingBid)
                .startTime(startTime)
                .endTime(endTime)
                .auctionStatus(AuctionStatus.ACTIVE)
                .auctionCategory(AuctionCategory.PRIVATE_FREE)
                .seller(owner)
                .build();
        return auctionRepository.save(auction);
    }

    private Product createProduct(String name, String description, User owner, Auction auction) {
        Product product = Product.builder()
                .name(name)
                .description(description)
                .owner(owner)
                .auction(auction)
                .build();
        return productRepository.save(product);
    }
}
