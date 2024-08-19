package com.tasksprints.auction.domain.review.dto;

import com.tasksprints.auction.domain.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    Integer rating;
    String content;
    public static ReviewDTO of(Review review) {
        return ReviewDTO.builder()
                .rating(review.getRating())
                .content(review.getContent())
                .build();
    }
}
