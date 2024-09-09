package com.tasksprints.auction.domain.bid.repository;

import com.tasksprints.auction.domain.bid.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query("select b from bids b where b.auction.id = :auctionId")
    List<Bid> findByAuctionId(Long auctionId);

    @Query("select b from bids b where b.auction.id = :auctionId and b.user.id = :userId")
    Optional<Bid> findByUserIdAndAuctionId(Long userId, Long auctionId);

    @Query("select b from bids b where b.uuid = :uuid")
    Optional<Bid> findByUuid(String uuid);
}
