package com.tasksprints.auction.domain.auction.service;

import com.tasksprints.auction.domain.auction.dto.response.AuctionResponse;
import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;

import java.util.List;

/**
 * 사용자가 맞는지 판단도 해야함.
 */
public interface AuctionService {
    AuctionResponse createAuction(Long userId, AuctionRequest.Create auctionRequest);

    void closeAuction(Long auctionId);
    String getAuctionStatus(Long auctionId);
    List<AuctionResponse> getAuctionsByUser(Long userId);
    List<AuctionResponse> getAllAuctions();
    AuctionResponse getAuctionById(Long auctionId);
}
