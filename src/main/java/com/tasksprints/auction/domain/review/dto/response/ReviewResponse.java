package com.tasksprints.auction.domain.review.dto.response;

import com.tasksprints.auction.domain.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    Integer rating;
    String content;
    public static ReviewResponse of(Review review) {
        return ReviewResponse.builder()
                .rating(review.getRating())
                .content(review.getContent())
                .build();
    }
}
