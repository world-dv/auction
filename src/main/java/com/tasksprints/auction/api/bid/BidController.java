package com.tasksprints.auction.api.bid;

import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.common.response.ApiResult;
import com.tasksprints.auction.domain.bid.dto.BidRequest;
import com.tasksprints.auction.domain.bid.dto.BidResponse;
import com.tasksprints.auction.domain.bid.service.BidService;
import com.tasksprints.auction.domain.socket.dto.AddChatRoomDto;
import com.tasksprints.auction.domain.socket.service.ChatService;
import com.tasksprints.auction.domain.user.dto.response.UserDetailResponse;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction")
public class BidController {
    private final BidService bidService;
    private final ChatService chatService;
    private final UserService userService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/bid")
    public void handleBid(BidRequest bidRequest) {
        /**
         * 입찰하는거 여기다가 추가하면 좋을 듯 합니다.
         */
        UserDetailResponse userDetailResponse = userService.getUserDetailsById(bidRequest.getUserId());
        if (chatService.isUserOwner(bidRequest.getChatRoomId(), userDetailResponse.getId())) {
            return;
        }

        BidResponse bidResponse = bidService.updateBidAmount(bidRequest.getUserId(), bidRequest.getAuctionId(),
            bidRequest.getAmount());
        simpMessageSendingOperations.convertAndSend("/bid/" + bidResponse.getUuid(), bidResponse);
    }

    @PostMapping("/{auctionId}/bid")
    @Operation(summary = "Submit a bid", description = "Submits a bid for the specified auction.")
    @ApiResponse(responseCode = "200", description = "Bid submitted successfully")
    public ResponseEntity<ApiResult<BidResponse>> submitBid(
        @Parameter(description = "ID of the user submitting the bid") @RequestParam Long userId,
        @PathVariable Long auctionId, @Parameter(description = "Bid amount") @RequestParam BigDecimal amount) {
        BidResponse bid = bidService.submitBid(userId, auctionId, amount);
        User user = userService.getUserById(userId);
        chatService.createRoom(new AddChatRoomDto(bid.getName(), user)); //입찰 생성 시 채팅방 생성 후 저장
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.BID_SUBMITTED_SUCCESS, bid));
    }

    @PutMapping("/{auctionId}/bid")
    @Operation(summary = "Update a bid", description = "Updates the amount of an existing bid.")
    @ApiResponse(responseCode = "200", description = "Bid updated successfully")
    public ResponseEntity<ApiResult<BidResponse>> updateBid(
        @Parameter(description = "ID of the user updating the bid") @RequestParam Long userId,
        @PathVariable Long auctionId, @Parameter(description = "New bid amount") @RequestParam BigDecimal amount) {
        BidResponse updatedBid = bidService.updateBidAmount(userId, auctionId, amount);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.BID_UPDATED_SUCCESS, updatedBid));
    }

    @GetMapping("/{auctionId}/bid/status")
    @Operation(summary = "Check user bid status", description = "Checks if the user has already placed a bid on the auction.")
    @ApiResponse(responseCode = "200", description = "Bid status checked successfully")
    public ResponseEntity<ApiResult<Boolean>> checkUserBidStatus(@PathVariable Long auctionId,
                                                                 @Parameter(description = "ID of the user") @RequestParam Long userId) {
        Boolean hasBid = bidService.hasUserAlreadyBid(auctionId);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.BID_STATUS_CHECKED, hasBid));
    }

    @GetMapping("/bid/{uuid}")
    @Operation(summary = "Get a bid", description = "Get a bid by bid uuid")
    @ApiResponse(responseCode = "200", description = "Bid status retrieved successfully")
    public ResponseEntity<ApiResult<BidResponse>> getBidByUuid(@PathVariable(value = "uuid") String uuid) {
        BidResponse bid = bidService.getBidByUuid(uuid);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.AUCTION_RETRIEVED, bid));
    }
}
