package com.tasksprints.auction.api.auction;

import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.common.response.ApiResult;
import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;
import com.tasksprints.auction.domain.auction.dto.response.AuctionResponse;
import com.tasksprints.auction.domain.auction.service.AuctionService;
import com.tasksprints.auction.domain.product.model.ProductCategory;
import com.tasksprints.auction.domain.review.dto.request.ReviewRequest;
import com.tasksprints.auction.domain.review.dto.response.ReviewResponse;
import com.tasksprints.auction.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auction")
@RequiredArgsConstructor
@Tag(name = "Auction", description = "Operations related to auctions")
public class AuctionController {
    private final AuctionService auctionService;
    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Create an auction", description = "Creates a new auction for a user.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Auction created successfully")})
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

    //    @GetMapping
//    @Operation(summary = "Get all auctions", description = "Retrieves all auctions.")
//    @ApiResponse(responseCode = "200", description = "All auctions retrieved successfully")
//    public ResponseEntity<ApiResult<List<AuctionResponse>>> getAllAuctions(@RequestParam(required = false) AuctionRequest.AuctionCategoryParam auctionCategory) {
//        List<AuctionResponse> allAuctions;
//        if (auctionCategory != null) {
//                allAuctions = auctionService.getAuctionsByAuctionCategory(auctionCategory);
//        } else {
//                allAuctions = auctionService.getAllAuctions();
//        }
//        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.ALL_AUCTIONS_RETRIEVED, allAuctions));
//    }
    @GetMapping
    @Operation(summary = "Get all auctions", description = "Retrieves all auctions.")
    @ApiResponse(responseCode = "200", description = "All auctions retrieved successfully")
    public ResponseEntity<ApiResult<Page<AuctionResponse.Details>>> getAllAuctions(Pageable pageable,
                                                                                   AuctionRequest.SearchCondition searchCondition) {
        Page<AuctionResponse.Details> auctions = auctionService.getAuctionsByFilter(pageable, searchCondition);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.ALL_AUCTIONS_RETRIEVED, auctions));
    }

    @GetMapping("/{auctionId}")
    @Operation(summary = "Get auction by ID", description = "Retrieves auction details by its ID.")
    @ApiResponse(responseCode = "200", description = "Auction retrieved successfully")
    public ResponseEntity<ApiResult<AuctionResponse>> getAuctionById(@PathVariable Long auctionId) {
        AuctionResponse auction = auctionService.getAuctionById(auctionId);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.AUCTION_RETRIEVED, auction));
    }

    @Deprecated
    @GetMapping("/category/{category}")
    @Operation(summary = "Get auctions by ProductCategory", description = "Retrieve all auction by its ProductCategory.")
    @ApiResponse(responseCode = "200", description = "All auctions retrieved successfully")
    public ResponseEntity<ApiResult<Page<AuctionResponse.Details>>> getAuctionByProductCategory(Pageable pageable,
                                                                                                @PathVariable String category,
                                                                                                AuctionRequest.SearchCondition searchCondition) {
        Page<AuctionResponse.Details> auctions = auctionService.getAuctionsByProductCategory(pageable, searchCondition,
            ProductCategory.fromDisplayName(category));
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.AUCTION_RETRIEVED, auctions));
    }

    // Review Endpoints
    @PostMapping("/{auctionId}/review")
    @Operation(summary = "Create a review", description = "Creates a review for a specific auction.")
    @ApiResponse(responseCode = "200", description = "Review created successfully")
    public ResponseEntity<ApiResult<ReviewResponse>> createReview(
        @Parameter(description = "ID of the user creating the review") @RequestParam Long userId,
        @PathVariable Long auctionId, @RequestBody ReviewRequest.Create reviewRequest) {
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
