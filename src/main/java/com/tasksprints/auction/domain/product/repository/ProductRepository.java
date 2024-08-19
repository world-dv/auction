package com.tasksprints.auction.domain.product.repository;

import com.tasksprints.auction.domain.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM products p WHERE p.auction.id = :auctionId")
    Optional<Product> findByAuctionId(Long auctionId);

    @Query("SELECT p FROM products p WHERE p.owner.id = :ownerId")
    List<Product> findAllByUserId(Long ownerId);
}
