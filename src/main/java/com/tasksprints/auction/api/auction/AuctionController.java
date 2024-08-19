package com.tasksprints.auction.api.auction;

import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.common.response.ApiResponse;
import com.tasksprints.auction.domain.auction.dto.AuctionDTO;
import com.tasksprints.auction.domain.auction.dto.AuctionRequest;
import com.tasksprints.auction.domain.auction.service.AuctionService;
import com.tasksprints.auction.domain.bid.dto.BidDTO;
import com.tasksprints.auction.domain.bid.service.BidService;
import com.tasksprints.auction.domain.review.dto.ReviewDTO;
import com.tasksprints.auction.domain.review.dto.ReviewRequest;
import com.tasksprints.auction.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auction")
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;
    private final BidService bidService;
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<AuctionDTO>> createAuction(@RequestParam Long userId, @RequestBody AuctionRequest.Create auctionRequest) {
        AuctionDTO createdAuction = auctionService.createAuction(userId, auctionRequest);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.AUCTION_CREATED_SUCCESS, createdAuction));
    }

    @PostMapping("/{auctionId}/close")
    public ResponseEntity<ApiResponse<String>> closeAuction(@PathVariable Long auctionId) {
        auctionService.closeAuction(auctionId);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.AUCTION_CLOSED_SUCCESS));
    }

    @GetMapping("/{auctionId}/status")
    public ResponseEntity<ApiResponse<String>> getAuctionStatus(@PathVariable Long auctionId) {
        String status = auctionService.getAuctionStatus(auctionId);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.AUCTION_STATUS_RETRIEVED, status));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<AuctionDTO>>> getAuctionsByUser(@PathVariable Long userId) {
        List<AuctionDTO> userAuctions = auctionService.getAuctionsByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.AUCTION_BY_USER_RETRIEVED, userAuctions));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuctionDTO>>> getAllAuctions() {
        List<AuctionDTO> allAuctions = auctionService.getAllAuctions();
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.ALL_AUCTIONS_RETRIEVED, allAuctions));
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<ApiResponse<AuctionDTO>> getAuctionById(@PathVariable Long auctionId) {
        AuctionDTO auction = auctionService.getAuctionById(auctionId);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.AUCTION_RETRIEVED, auction));
    }

    // Bid Endpoints
    @PostMapping("/{auctionId}/bid")
    public ResponseEntity<ApiResponse<BidDTO>> submitBid(@RequestParam Long userId, @PathVariable Long auctionId, @RequestParam BigDecimal amount) {
        BidDTO bid = bidService.submitBid(userId, auctionId, amount);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.BID_SUBMITTED_SUCCESS, bid));
    }

    @PutMapping("/{auctionId}/bid")
    public ResponseEntity<ApiResponse<BidDTO>> updateBid(@RequestParam Long userId, @PathVariable Long auctionId, @RequestParam BigDecimal amount) {
        BidDTO updatedBid = bidService.updateBidAmount(userId, auctionId, amount);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.BID_UPDATED_SUCCESS, updatedBid));
    }

    @GetMapping("/{auctionId}/bid/status")
    public ResponseEntity<ApiResponse<Boolean>> checkUserBidStatus(@PathVariable Long auctionId, @RequestParam Long userId) {
        Boolean hasBidded = bidService.hasUserAlreadyBid(auctionId);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.BID_STATUS_CHECKED, hasBidded));
    }

    // Review Endpoints
    @PostMapping("/{auctionId}/review")
    public ResponseEntity<ApiResponse<ReviewDTO>> createReview(@RequestParam Long userId, @PathVariable Long auctionId, @RequestBody ReviewRequest.Create reviewRequest) {
        ReviewDTO createdReview = reviewService.createReview(userId, auctionId, reviewRequest);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.REVIEW_CREATED_SUCCESS, createdReview));
    }

    @GetMapping("/user/{userId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewDTO>>> getReviewsByUser(@PathVariable Long userId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.REVIEWS_RETRIEVED, reviews));
    }

    @GetMapping("/{auctionId}/review")
    public ResponseEntity<ApiResponse<ReviewDTO>> getReviewByAuctionId(@PathVariable Long auctionId) {
        ReviewDTO review = reviewService.getReviewByAuctionId(auctionId);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.REVIEW_RETRIEVED, review));
    }
}
