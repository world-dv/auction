package com.tasksprints.auction.domain.review.dto.request;

import lombok.Data;

public class ReviewRequest {
    @Data
    public static class Create{
        Integer rating;
        String content;
    }
}
