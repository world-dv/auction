package com.tasksprints.auction.domain.review.service;

import com.tasksprints.auction.domain.review.dto.ReviewDTO;
import com.tasksprints.auction.domain.review.dto.ReviewRequest;

import java.util.List;

public interface ReviewService {

    ReviewDTO createReview(Long userId, Long auctionId, ReviewRequest.Create review);

    List<ReviewDTO> getReviewsByUserId(Long userId);

    ReviewDTO getReviewByAuctionId(Long auctionId);
    
}
/**
 * 수정, 삭제 불가
 */
