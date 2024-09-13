package com.tasksprints.auction.domain.auction.repository;

import com.tasksprints.auction.common.config.QueryDslConfig;
import com.tasksprints.auction.domain.auction.dto.response.AuctionResponse;
import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(QueryDslConfig.class)
@Slf4j
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

    private Auction createAuction(User seller, AuctionCategory category, AuctionStatus status) {
        return Auction.create(
            LocalDateTime.of(2024, 8, 1, 10, 0),
            LocalDateTime.of(2024, 9, 1, 10, 0),
            BigDecimal.valueOf(100.00),
            category,
            status,
            seller
        );
    }

    private Auction createAuction(User seller, LocalDateTime startTime) {
        return Auction.create(
            startTime,
            LocalDateTime.of(2024, 9, 1, 10, 0),
            BigDecimal.valueOf(100.00),
            AuctionCategory.PUBLIC_PAID,
            AuctionStatus.PENDING,
            seller
        );
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

    @Test
    @DisplayName("경매 유형이 []인 경매 목록 조회")
    public void testFindAuctionsByAuctionCategory() {
        //given
        Auction auction1 = createAuction(seller, AuctionCategory.PUBLIC_FREE, AuctionStatus.ACTIVE);
        Auction auction2 = createAuction(seller, AuctionCategory.PUBLIC_PAID, AuctionStatus.PENDING);
        Auction auction3 = createAuction(seller, AuctionCategory.PUBLIC_PAID, AuctionStatus.PENDING);
        AuctionRequest.SearchCondition condition = new AuctionRequest.SearchCondition(AuctionCategory.PUBLIC_PAID, null, null, null, null, null, null, null);
        auctionRepository.save(auction1);
        auctionRepository.save(auction2);
        auctionRepository.save(auction3);

        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<AuctionResponse> auctions = auctionRepository.getAuctionsByFilters(pageable, condition);

        //then
        assertThat(auctions).hasSize(2);
        assertThat(auctions.getContent()).allMatch(auction -> auction.getCategory().equals(AuctionCategory.PUBLIC_PAID.name()));
    }

    @Test
    @DisplayName("QueryDSL 필터를 통해서 경매 목록 조회")
    public void testFindAllUsingFilter() {
        Auction auction1 = createAuction(seller, AuctionCategory.PUBLIC_FREE, AuctionStatus.ACTIVE);
        Auction auction2 = createAuction(seller, AuctionCategory.PUBLIC_PAID, AuctionStatus.PENDING);
        AuctionRequest.SearchCondition condition = new AuctionRequest.SearchCondition(AuctionCategory.PUBLIC_FREE, null, null, null, null, null, null, null);
        auctionRepository.save(auction1);
        auctionRepository.save(auction2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<AuctionResponse> auctions = auctionRepository.getAuctionsByFilters(pageable, condition);
        log.info(auctions.toString());

        assertThat(auctions).hasSize(1);
        assertThat(auctions.getContent().get(0).getCategory()).isEqualTo(AuctionCategory.PUBLIC_FREE.name());
        assertThat(auctions.getContent().get(0).getCategory()).isNotEqualTo(AuctionCategory.PUBLIC_PAID.name());


    }

}
