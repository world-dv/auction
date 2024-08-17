package com.tasksprints.auction.domain.auction;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import com.tasksprints.auction.domain.auction.repository.AuctionRepository;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class AuctionRepositoryTest {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepository userRepository;

    private User seller;

    @BeforeEach
    public void setUp() {
        seller = User.builder()
                .name("testUser")
                .nickName("testNick")
                .password("testPassword")
                .email("test@example.com")
                .build();
        userRepository.save(seller);
    }

    @Test
    @DisplayName("사용자 ID로 경매 목록 조회")
    public void testFindAuctionsByUserId() {
        Auction auction = createAuction(seller, AuctionCategory.PUBLIC_FREE, AuctionStatus.ACTIVE);
        auctionRepository.save(auction);

        List<Auction> auctions = auctionRepository.findAuctionsByUserId(seller.getId());

        assertThat(auctions).isNotEmpty();
        assertThat(auctions.get(0).getSeller().getId()).isEqualTo(seller.getId());
    }

    @Test
    @DisplayName("경매 ID로 경매 조회")
    public void testFindAuctionById() {
        Auction auction = createAuction(seller, AuctionCategory.PUBLIC_FREE, AuctionStatus.PENDING);
        auctionRepository.save(auction);

        Optional<Auction> foundAuction = auctionRepository.findAuctionById(auction.getId());

        assertTrue(foundAuction.isPresent());
        assertThat(foundAuction.get().getAuctionCategory()).isEqualTo(AuctionCategory.PUBLIC_FREE);
    }

    @Test
    @DisplayName("모든 경매 목록 조회")
    public void testFindAll() {
        Auction auction1 = createAuction(seller, AuctionCategory.PUBLIC_FREE, AuctionStatus.ACTIVE);
        Auction auction2 = createAuction(seller, AuctionCategory.PUBLIC_PAID, AuctionStatus.PENDING);

        auctionRepository.save(auction1);
        auctionRepository.save(auction2);

        List<Auction> auctions = auctionRepository.findAll();

        assertThat(auctions).hasSize(2);
    }

    private Auction createAuction(User seller, AuctionCategory category, AuctionStatus status) {
        return Auction.create(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                BigDecimal.valueOf(100.00),
                category,
                status,
                seller
        );
    }
}
