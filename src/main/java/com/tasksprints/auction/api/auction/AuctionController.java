package com.tasksprints.auction.api.auction;

import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.common.response.ApiResult;
import com.tasksprints.auction.domain.auction.dto.response.AuctionResponse;
import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;
import com.tasksprints.auction.domain.auction.service.AuctionService;
import com.tasksprints.auction.domain.bid.dto.BidResponse;
import com.tasksprints.auction.domain.bid.service.BidService;
import com.tasksprints.auction.domain.review.dto.response.ReviewResponse;
import com.tasksprints.auction.domain.review.dto.request.ReviewRequest;
import com.tasksprints.auction.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
@RestController
@RequestMapping("/api/v1/auction")
@RequiredArgsConstructor
@Tag(name = "Auction", description = "Operations related to auctions")
public class AuctionController {
    private final AuctionService auctionService;
    private final BidService bidService;
    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Create an auction", description = "Creates a new auction for a user.")
    @ApiResponses(
            value = {
                    @ApiResponse( responseCode = "200", description = "Auction created successfully")
            })
    public ResponseEntity<ApiResult<AuctionResponse>> createAuction(
            @Parameter(description = "ID of the user creating the auction") @RequestParam Long userId,
            @RequestBody AuctionRequest.Create auctionRequest) {
        AuctionResponse createdAuction = auctionService.createAuction(userId, auctionRequest);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.AUCTION_CREATED_SUCCESS, createdAuction));
    }

    @PostMapping("/{auctionId}/close")
    @Operation(summary = "Close an auction", description = "Closes the auction by its ID.")
    @ApiResponse(responseCode = "200", description = "Auction closed successfully")
    public ResponseEntity<ApiResult<String>> closeAuction(@PathVariable Long auctionId) {
        auctionService.closeAuction(auctionId);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.AUCTION_CLOSED_SUCCESS));
    }

    @GetMapping("/{auctionId}/status")
    @Operation(summary = "Get auction status", description = "Retrieves the status of the auction.")
    @ApiResponse(responseCode = "200", description = "Auction status retrieved successfully")
    public ResponseEntity<ApiResult<String>> getAuctionStatus(@PathVariable Long auctionId) {
        String status = auctionService.getAuctionStatus(auctionId);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.AUCTION_STATUS_RETRIEVED, status));
    }
// 필터로 추가


//    @GetMapping("/")
//    @Operation(summary = "Get auctions by user", description = "Retrieves all auctions created by a specific user.")
//    @ApiResponse(responseCode = "200", description = "User auctions retrieved successfully")
//    public ResponseEntity<ApiResult<List<AuctionDTO>>> getAuctionsByUser(@RequestParam Long userId) {
//        List<AuctionDTO> userAuctions = auctionService.getAuctionsByUser(userId);
//        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.AUCTION_BY_USER_RETRIEVED, userAuctions));
//    }

    @GetMapping
    @Operation(summary = "Get all auctions", description = "Retrieves all auctions.")
    @ApiResponse(responseCode = "200", description = "All auctions retrieved successfully")
    public ResponseEntity<ApiResult<List<AuctionResponse>>> getAllAuctions() {
        List<AuctionResponse> allAuctions = auctionService.getAllAuctions();
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.ALL_AUCTIONS_RETRIEVED, allAuctions));
    }

    @GetMapping("/{auctionId}")
    @Operation(summary = "Get auction by ID", description = "Retrieves auction details by its ID.")
    @ApiResponse(responseCode = "200", description = "Auction retrieved successfully")
    public ResponseEntity<ApiResult<AuctionResponse>> getAuctionById(@PathVariable Long auctionId) {
        AuctionResponse auction = auctionService.getAuctionById(auctionId);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.AUCTION_RETRIEVED, auction));
    }

    // Bid Endpoints
    @PostMapping("/{auctionId}/bid")
    @Operation(summary = "Submit a bid", description = "Submits a bid for the specified auction.")
    @ApiResponse(responseCode = "200", description = "Bid submitted successfully")
    public ResponseEntity<ApiResult<BidResponse>> submitBid(
            @Parameter(description = "ID of the user submitting the bid") @RequestParam Long userId,
            @PathVariable Long auctionId,
            @Parameter(description = "Bid amount") @RequestParam BigDecimal amount) {
        BidResponse bid = bidService.submitBid(userId, auctionId, amount);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.BID_SUBMITTED_SUCCESS, bid));
    }

    @PutMapping("/{auctionId}/bid")
    @Operation(summary = "Update a bid", description = "Updates the amount of an existing bid.")
    @ApiResponse(responseCode = "200", description = "Bid updated successfully")
    public ResponseEntity<ApiResult<BidResponse>> updateBid(
            @Parameter(description = "ID of the user updating the bid") @RequestParam Long userId,
            @PathVariable Long auctionId,
            @Parameter(description = "New bid amount") @RequestParam BigDecimal amount) {
        BidResponse updatedBid = bidService.updateBidAmount(userId, auctionId, amount);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.BID_UPDATED_SUCCESS, updatedBid));
    }

    @GetMapping("/{auctionId}/bid/status")
    @Operation(summary = "Check user bid status", description = "Checks if the user has already placed a bid on the auction.")
    @ApiResponse(responseCode = "200", description = "Bid status checked successfully")
    public ResponseEntity<ApiResult<Boolean>> checkUserBidStatus(
            @PathVariable Long auctionId,
            @Parameter(description = "ID of the user") @RequestParam Long userId) {
        Boolean hasBidded = bidService.hasUserAlreadyBid(auctionId);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.BID_STATUS_CHECKED, hasBidded));
    }

    // Review Endpoints
    @PostMapping("/{auctionId}/review")
    @Operation(summary = "Create a review", description = "Creates a review for a specific auction.")
    @ApiResponse(responseCode = "200", description = "Review created successfully")
    public ResponseEntity<ApiResult<ReviewResponse>> createReview(
            @Parameter(description = "ID of the user creating the review") @RequestParam Long userId,
            @PathVariable Long auctionId,
            @RequestBody ReviewRequest.Create reviewRequest) {
        ReviewResponse createdReview = reviewService.createReview(userId, auctionId, reviewRequest);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.REVIEW_CREATED_SUCCESS, createdReview));
    }


    @GetMapping("/{auctionId}/review")
    @Operation(summary = "Get review by auction ID", description = "Retrieves the review for a specific auction.")
    @ApiResponse(responseCode = "200", description = "Review retrieved successfully")
    public ResponseEntity<ApiResult<ReviewResponse>> getReviewByAuctionId(@PathVariable Long auctionId) {
        ReviewResponse review = reviewService.getReviewByAuctionId(auctionId);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.REVIEW_RETRIEVED, review));
    }
}