package com.tasksprints.auction.domain.auction.repository;


import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.repository.support.AuctionCriteriaRepository;
import com.tasksprints.auction.domain.product.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long>, AuctionCriteriaRepository {


    @Query("SELECT a FROM auction a WHERE a.seller.id = :userId")
    List<Auction> findAuctionsByUserId(@Param("userId") Long userId);

    @Query("SELECT a FROM auction a WHERE a.id = :auctionId")
    Optional<Auction> findAuctionById(@Param("auctionId") Long auctionId);

    List<Auction> findAuctionsByAuctionCategory(AuctionCategory auctionCategory);

    List<Auction> findAuctionByProduct_Category(ProductCategory productCategory);
}

