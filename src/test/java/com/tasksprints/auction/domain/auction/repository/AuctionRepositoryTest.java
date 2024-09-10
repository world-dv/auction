package com.tasksprints.auction.domain.auction.repository;

import com.tasksprints.auction.common.config.QueryDslConfig;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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

//    private Auction createAuction(LocalDateTime endTime, AuctionStatus status) {
//        return Auction.create(
//            LocalDateTime.of(2024, 8, 1, 10, 0),
//            endTime,
//            BigDecimal.valueOf(100.00),
//            AuctionCategory.PUBLIC_PAID,
//            status,
//            seller
//        );
//    }

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

//    @Test
//    @DisplayName("경매 유형이 []인 경매 목록 조회")
//    public void testFindAuctionsByAuctionCategory() {
//        //given
//        Auction auction1 = createAuction(seller, AuctionCategory.PUBLIC_FREE, AuctionStatus.ACTIVE);
//        Auction auction2 = createAuction(seller, AuctionCategory.PUBLIC_PAID, AuctionStatus.PENDING);
//        Auction auction3 = createAuction(seller, AuctionCategory.PUBLIC_PAID, AuctionStatus.PENDING);
//
//        auctionRepository.save(auction1);
//        auctionRepository.save(auction2);
//        auctionRepository.save(auction3);
//
//        //when
//        List<Auction> auctions = auctionRepository.findAuctionsByAuctionCategory(AuctionCategory.PUBLIC_PAID);
//
//        //then
//        assertThat(auctions).hasSize(2);
//        assertThat(auctions).allMatch(auction -> auction.getAuctionCategory() == AuctionCategory.PUBLIC_PAID);
//    }

    @Test
    @DisplayName("QueryDSL 필터를 통해서 경매 목록 조회")
    public void testFindAllUsingFilter() {
        Auction auction1 = createAuction(seller, AuctionCategory.PUBLIC_FREE, AuctionStatus.ACTIVE);
        Auction auction2 = createAuction(seller, AuctionCategory.PUBLIC_PAID, AuctionStatus.PENDING);

        auctionRepository.save(auction1);
        auctionRepository.save(auction2);

        List<Auction> auctions = auctionRepository.getAuctionsByFilters(null, AuctionCategory.PUBLIC_FREE, null, null, null);
        log.info(auctions.toString());

        assertThat(auctions).hasSize(1);
        assertThat(auctions.get(0).getAuctionCategory()).isEqualTo(AuctionCategory.PUBLIC_FREE);
        assertThat(auctions.get(0).getAuctionCategory()).isNotEqualTo(AuctionCategory.PUBLIC_PAID);


    }

//    @Test
//    @DisplayName("경매 마감 시간까지 24시간 이하로 남은 경매 목록 조회")
//    public void testFindAuctionsByEndTimeBetweenOrderByEndTimeAsc() {
//        //given
//        LocalDateTime fixedNow = LocalDateTime.of(2024, 9, 1, 10, 0);
//        LocalDateTime next24Hours = fixedNow.plusHours(24);
//
//        List<Auction> auctions = List.of(
//            createAuction(fixedNow.plusHours(23), AuctionStatus.ACTIVE),
//            createAuction(fixedNow.plusHours(22), AuctionStatus.ACTIVE),
//            createAuction(fixedNow.plusHours(21), AuctionStatus.ACTIVE),
//            createAuction(fixedNow.plusHours(48), AuctionStatus.ACTIVE),
//            createAuction(fixedNow.plusHours(20), AuctionStatus.PENDING)
//        );
//
//        auctionRepository.saveAll(auctions);
//
//        // when
//        List<Auction> result = auctionRepository.getAuctionsEndWith24Hours(fixedNow, next24Hours, AuctionStatus.ACTIVE);
//
//        //then
//        assertThat(result).hasSize(3);
//
//        assertAll("endTime을 기준으로 오름차순 정렬이 되었는지 확인",
//            () -> {
//                LocalDateTime previousEndTime = null;
//                for (Auction auction : result) {
//                    if (previousEndTime != null) {
//                        assertThat(auction.getEndTime()).isAfterOrEqualTo(previousEndTime);
//                    }
//                    previousEndTime = auction.getEndTime();
//                }
//            }
//        );
//    }

}
