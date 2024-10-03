package com.tasksprints.auction.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasksprints.auction.api.auction.AuctionController;
import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;
import com.tasksprints.auction.domain.auction.dto.response.AuctionResponse;
import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import com.tasksprints.auction.domain.auction.service.AuctionService;
import com.tasksprints.auction.domain.bid.dto.BidResponse;
import com.tasksprints.auction.domain.bid.service.BidService;
import com.tasksprints.auction.domain.review.dto.request.ReviewRequest;
import com.tasksprints.auction.domain.review.dto.response.ReviewResponse;
import com.tasksprints.auction.domain.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuctionController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class AuctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuctionService auctionService;

    @MockBean
    private BidService bidService;

    @MockBean
    private ReviewService reviewService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("경매 생성 성공")
    public void testCreateAuction_Success() throws Exception {
        AuctionRequest.Create auctionRequest = new AuctionRequest.Create();
        AuctionResponse auctionDTO = new AuctionResponse(); // Populate with necessary fields

        when(auctionService.createAuction(anyLong(), any())).thenReturn(auctionDTO);

        mockMvc.perform(post("/api/v1/auction")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auctionRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(ApiResponseMessages.AUCTION_CREATED_SUCCESS));
    }

    @Test
    @DisplayName("경매 생성 실패")
    public void testCreateAuction_Failure() throws Exception {
        AuctionRequest.Create auctionRequest = new AuctionRequest.Create();

        when(auctionService.createAuction(anyLong(), any())).thenThrow(new RuntimeException("Error creating auction"));

        mockMvc.perform(post("/api/v1/auction")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auctionRequest)))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.message").value("Error creating auction"));
    }

    @Test
    @DisplayName("경매 종료 성공")
    public void testCloseAuction_Success() throws Exception {
        mockMvc.perform(post("/api/v1/auction/1/close"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(ApiResponseMessages.AUCTION_CLOSED_SUCCESS));

        verify(auctionService, times(1)).closeAuction(1L);
    }

    @Test
    @DisplayName("경매 상태 조회 성공")
    public void testGetAuctionStatus_Success() throws Exception {
        String auctionStatus = "OPEN";
        when(auctionService.getAuctionStatus(1L)).thenReturn(auctionStatus);

        mockMvc.perform(get("/api/v1/auction/1/status"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(ApiResponseMessages.AUCTION_STATUS_RETRIEVED))
            .andExpect(jsonPath("$.data").value(auctionStatus));
    }

    @Test
    @DisplayName("QueryString을 통한 경매 목록 조회")
    public void testFindAuctionByUsingQueryString_Success() throws Exception {
        // Given
        List<AuctionResponse.Details> auctionResponseList = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 10);
        Page<AuctionResponse.Details> pageAuctionResponse = new PageImpl<>(auctionResponseList, pageable, 0);
        when(auctionService.getAuctionsByFilter(any(),any())).thenReturn(pageAuctionResponse);

        // When & Then
        mockMvc.perform(get("/api/v1/auction")
                .param("auctionCategory", AuctionCategory.PRIVATE_FREE.name())
                .param("productCategory", "여성의류")
                .param("startTime", LocalDateTime.now().minusDays(1).toString())
                .param("endTime", LocalDateTime.now().toString())
                .param("minPrice", "100")
                .param("maxPrice", "500")
                .param("auctionStatus", AuctionStatus.ACTIVE.name())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(ApiResponseMessages.ALL_AUCTIONS_RETRIEVED));
    }

//    @Test
//    @DisplayName("잘못된 유형을 통한 경매목록 조회(기본값으로 대응)")
//    public void testFindAuctionByUsingWrongAuctionCategory_Success() throws Exception {
//        List<AuctionResponse> auctionResponseList = new ArrayList<>();
//        when(auctionService.getAuctionsByFilter(any(), any())).thenReturn(auctionResponseList);
//
//        mockMvc.perform(get("/api/v1/auction")
//                .param("auctionCategory", "NON"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.message").value(ApiResponseMessages.ALL_AUCTIONS_RETRIEVED));
//    }

//    @Test
//    @DisplayName("마감 기한이 24시간 이하로 남은 경매 목록 조회")
//    public void testFindAuctionsEndWithin24Hours_Success() throws Exception {
//
//        /*
//        테스트 코드 추가
//        * */
//
//    }

    @Test
    @DisplayName("입찰 제출 성공")
    public void testSubmitBid_Success() throws Exception {
        BidResponse bidDTO = new BidResponse(); // Populate with necessary fields
        when(bidService.submitBid(anyLong(), anyLong(), any())).thenReturn(bidDTO);

        mockMvc.perform(post("/api/v1/auction/1/bid")
                .param("userId", "1")
                .param("amount", "100.00"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(ApiResponseMessages.BID_SUBMITTED_SUCCESS));
    }

    @Test
    @DisplayName("입찰 금액 업데이트 성공")
    public void testUpdateBid_Success() throws Exception {
        BidResponse updatedBidDTO = new BidResponse(); // Populate with necessary fields
        when(bidService.updateBidAmount(anyLong(), anyLong(), any())).thenReturn(updatedBidDTO);

        mockMvc.perform(put("/api/v1/auction/1/bid")
                .param("userId", "1")
                .param("amount", "150.00"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(ApiResponseMessages.BID_UPDATED_SUCCESS));
    }

    @Test
    @DisplayName("리뷰 생성 성공")
    public void testCreateReview_Success() throws Exception {
        ReviewRequest.Create reviewRequest = new ReviewRequest.Create();
        ReviewResponse reviewResponse = new ReviewResponse(); // Populate with necessary fields
        when(reviewService.createReview(anyLong(), anyLong(), any())).thenReturn(reviewResponse);

        mockMvc.perform(post("/api/v1/auction/1/review")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(ApiResponseMessages.REVIEW_CREATED_SUCCESS));
    }


//    @Test
//    @DisplayName("사용자 리뷰 조회 성공")
//    public void testGetReviewsByUser_Success() throws Exception {
//        List<ReviewDTO> reviews = Collections.singletonList(new ReviewDTO()); // Populate with necessary fields
//        when(reviewService.getReviewsByUserId(anyLong())).thenReturn(reviews);
//
//        mockMvc.perform(get("/api/v1/auction/user/1/reviews"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value(ApiResponseMessages.REVIEWS_RETRIEVED))
//                .andExpect(jsonPath("$.data").isArray());
//    }
}
