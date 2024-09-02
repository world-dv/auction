package com.tasksprints.auction.domain.product.repository;

import com.tasksprints.auction.domain.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByAuctionId(Long auctionId);

    // 쿼리를 메서드 이름으로 표현
    List<Product> findByOwnerId(Long ownerId);
}
