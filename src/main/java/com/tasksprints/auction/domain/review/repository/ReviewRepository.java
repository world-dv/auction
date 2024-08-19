package com.tasksprints.auction.domain.review.repository;

import com.tasksprints.auction.domain.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from reviews r where r.writer.id = :userId")
    List<Review> findByUserId(Long userId);

    @Query("select r from reviews r where r.auction.id = :auctionId")
    Optional<Review> findByAuctionId(Long auctionId);
}
