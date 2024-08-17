package com.tasksprints.auction.api.auction;

import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.common.response.ApiResponse;
import com.tasksprints.auction.domain.auction.dto.AuctionDTO;
import com.tasksprints.auction.domain.auction.dto.AuctionRequest;
import com.tasksprints.auction.domain.auction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auction")
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService auctionService;

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
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.AUCTION_STATUS_RETRIEVED,status));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<AuctionDTO>>> getAuctionsByUser(@PathVariable Long userId) {
        List<AuctionDTO> userAuctions = auctionService.getAuctionsByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.AUCTION_BY_USER_RETRIEVED,userAuctions));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuctionDTO>>> getAllAuctions() {
        List<AuctionDTO> allAuctions = auctionService.getAllAuctions();
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.ALL_AUCTIONS_RETRIEVED,allAuctions));
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<ApiResponse<AuctionDTO>> getAuctionById(@PathVariable Long auctionId) {
        AuctionDTO auction = auctionService.getAuctionById(auctionId);
        return ResponseEntity.ok(ApiResponse.success(ApiResponseMessages.AUCTION_RETRIEVED, auction));
    }


}
