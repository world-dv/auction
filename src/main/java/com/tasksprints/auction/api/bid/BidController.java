package com.tasksprints.auction.api.bid;

import com.tasksprints.auction.common.constant.ApiResponseMessages;
import com.tasksprints.auction.common.response.ApiResult;
import com.tasksprints.auction.domain.bid.dto.BidResponse;
import com.tasksprints.auction.domain.bid.service.BidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bid")
@RequiredArgsConstructor
@Tag(name = "Bid", description = "Operations related to bids")
public class BidController {
    private final BidService bidService;

    @GetMapping("/{uuid}")
    @Operation(summary = "Get a bid", description = "Get a bid by bid uuid")
    @ApiResponse(responseCode = "200", description = "Bid status retrieved sucessfully")
    public ResponseEntity<ApiResult<BidResponse>> getBidByUuid(@PathVariable(value = "uuid") String uuid) {
        BidResponse bid = bidService.getBidByUuid(uuid);
        return ResponseEntity.ok(
                ApiResult.success(ApiResponseMessages.AUCTION_RETRIEVED, bid));
    }
}
