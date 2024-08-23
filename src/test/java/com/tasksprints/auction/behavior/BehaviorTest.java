package com.tasksprints.auction.behavior;

import com.tasksprints.auction.domain.auction.service.AuctionService;
import com.tasksprints.auction.domain.auction.service.AuctionServiceImpl;
import com.tasksprints.auction.domain.bid.service.BidService;
import com.tasksprints.auction.domain.bid.service.BidServiceImpl;
import com.tasksprints.auction.domain.product.service.ProductService;
import com.tasksprints.auction.domain.product.service.ProductServiceImpl;
import com.tasksprints.auction.domain.review.service.ReviewServiceImpl;
import com.tasksprints.auction.domain.user.dto.UserDetailsDTO;
import com.tasksprints.auction.domain.user.dto.UserRequest;
import com.tasksprints.auction.domain.user.dto.UserSummaryDTO;
import com.tasksprints.auction.domain.user.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BehaviorTest {
    @Autowired
    private AuctionServiceImpl auctionService;
    @Autowired
    private BidServiceImpl bidService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ReviewServiceImpl reviewService;
    @Autowired
    private UserServiceImpl userService;
    Long logOnUserId = null;
    @Test
    @Order(1)
    void 사용자_가입() {
        UserRequest.Register request = new UserRequest.Register("강태현", "test@email.com", "test", "Test");

        UserDetailsDTO userDetailsDTO = userService.createUser(request);
        log.info(userDetailsDTO.toString());
    }

    @Test
    @Order(2)
    void 사용자_로그인() {
        List<UserSummaryDTO> users= userService.getUsersSummary();
        log.info(users.toString());
        /**
         * 로그인했다고 가정
         */
        logOnUserId = users.get(0).getId();
        log.info(logOnUserId.toString());
    }

    @Test
    @Order(3)
    void 제품_등록() {
        /**url return**/
        
    }

    @Test
    @Order(4)
    void 등록_제품을_근거하여_경매생성() {
        /**
         * url을 같이 보냄
         */
    }

    @Test
    @Order(5)
    void 입찰_진행() {

    }

    @Test
    @Order(6)
    void 입찰_금액_올리기() {

    }

    @Test
    @Order(7)
    void 리뷰_작성() {

    }

    @Test
    @Order(8)
    void 리뷰_확인() {

    }

    @Test
    @Order(9)
    void 최근_입찰_내역_조회() {

    }

    @Test
    @Order(10)
    void 내가_쓴_리뷰_조회() {

    }

    @Test
    @Order(11)
    void 사용자_수정() {

    }

    @Test
    @Order(12)
    void 사용자_탈퇴() {

    }
}
