package com.tasksprints.auction.domain.product.repository;

import com.tasksprints.auction.domain.product.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
