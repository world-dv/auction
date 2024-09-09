package com.tasksprints.auction.domain.review.service;

import com.tasksprints.auction.domain.review.dto.response.ReviewResponse;
import com.tasksprints.auction.domain.review.dto.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(Long userId, Long auctionId, ReviewRequest.Create review);

    List<ReviewResponse> getReviewsByUserId(Long userId);

    ReviewResponse getReviewByAuctionId(Long auctionId);
    
}
/**
 * 수정, 삭제 불가
 */
