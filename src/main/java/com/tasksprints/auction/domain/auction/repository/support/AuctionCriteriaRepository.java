package com.tasksprints.auction.domain.auction.repository.support;

import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;
import com.tasksprints.auction.domain.auction.model.Auction;

import java.util.List;

public interface AuctionCriteriaRepository {
    List<Auction> getAuctionsByFilters(AuctionRequest.SearchCondition searchCondition);
}
