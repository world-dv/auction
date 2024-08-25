package com.tasksprints.auction.domain.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDTO {
    private Long productId;
    private String name;
    private String description;
}
