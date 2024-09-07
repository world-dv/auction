package com.tasksprints.auction.domain.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductRequest {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Register {
        private String name;
        private String description;
        private String category;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        private Long productId;
        private String name;
        private String description;
    }

}
