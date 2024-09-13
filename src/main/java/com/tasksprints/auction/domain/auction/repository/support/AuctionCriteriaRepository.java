package com.tasksprints.auction.domain.auction.repository.support;

import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;
import com.tasksprints.auction.domain.auction.dto.response.AuctionResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AuctionCriteriaRepository {
    Page<AuctionResponse> getAuctionsByFilters(Pageable pageable, AuctionRequest.SearchCondition searchCondition);

}
