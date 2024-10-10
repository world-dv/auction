package com.tasksprints.auction.api.bid;

import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.common.response.ApiResult;
import com.tasksprints.auction.domain.bid.dto.BidRequest;
import com.tasksprints.auction.domain.bid.dto.BidResponse;
import com.tasksprints.auction.domain.bid.service.BidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/bid")
public class BidController {
    private final BidService bidService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/bid")
    public void handleBid(BidRequest bidRequest) {
        /**
         * 입찰하는거 여기다가 추가하면 좋을 듯 합니다g.
         */
        BidResponse bidResponse = bidService.submitBid(bidRequest.getUserId(), bidRequest.getAuctionId(), bidRequest.getAmount());
        simpMessageSendingOperations.convertAndSend("/bid/"+bidResponse.getUuid(), bidResponse);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get a bid", description = "Get a bid by bid uuid")
    @ApiResponse(responseCode = "200", description = "Bid status retrieved successfully")
    public ResponseEntity<ApiResult<BidResponse>> getBidByUuid(@PathVariable(value = "uuid") String uuid) {
        BidResponse bid = bidService.getBidByUuid(uuid);
        return ResponseEntity.ok(ApiResult.success(ApiResponseMessages.AUCTION_RETRIEVED, bid));
    }
}
