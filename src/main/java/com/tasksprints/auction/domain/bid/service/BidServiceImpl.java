package com.tasksprints.auction.domain.bid.service;

import com.tasksprints.auction.domain.auction.exception.AuctionEndedException;
import com.tasksprints.auction.domain.auction.exception.AuctionNotFoundException;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.repository.AuctionRepository;
import com.tasksprints.auction.domain.bid.dto.BidResponse;
import com.tasksprints.auction.domain.bid.exception.BidNotFoundException;
import com.tasksprints.auction.domain.bid.exception.InvalidBidAmountException;
import com.tasksprints.auction.domain.bid.model.Bid;
import com.tasksprints.auction.domain.bid.repository.BidRepository;
import com.tasksprints.auction.domain.user.exception.UserNotFoundException;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 실시간성으로 인해서 socket으로 대체할지에 대한 여부 고민 필요
 */
@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    @Override
    public BidResponse submitBid(Long userId, Long auctionId, BigDecimal amount) {
        // 입찰 시 유효성 검사
        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Auction foundAuction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException("Auction not found"));

        // 경매가 종료되었는지 확인
        if (foundAuction.getEndTime().isBefore(LocalDateTime.now())) {
            throw new AuctionEndedException("This auction has already ended.");
        }

        // 최소 입찰 금액 충족 여부 확인
        if (amount.compareTo(foundAuction.getStartingBid()) < 0) {
            throw new InvalidBidAmountException("Bid amount is less than the minimum required bid amount.");
        }

        // 입찰 생성 및 저장
        Bid createdBid = Bid.create(amount, foundUser, foundAuction);
        Bid savedBid = bidRepository.save(createdBid);
        return BidResponse.of(savedBid);
    }

    @Override
    public BidResponse updateBidAmount(Long userId, Long auctionId, BigDecimal newAmount) {
        // 기존 입찰을 찾습니다.
        Bid foundBid = bidRepository.findByUserIdAndAuctionId(userId, auctionId)
                .orElseThrow(() -> new BidNotFoundException("Bid not found"));

        Auction foundAuction = foundBid.getAuction();

        // 경매가 종료되었는지 확인합니다.
        if (foundAuction.getEndTime().isBefore(LocalDateTime.now())) {
            throw new AuctionEndedException("This auction has already ended.");
        }

        // 새로운 입찰 금액이 기존 금액보다 큰지 확인합니다.
        if (newAmount.compareTo(foundBid.getAmount()) <= 0) {
            throw new InvalidBidAmountException("New bid amount must be greater than the previous bid amount.");
        }

        // 새로운 입찰 금액이 최소 입찰 금액을 충족하는지 확인합니다.
        if (newAmount.compareTo(foundAuction.getStartingBid()) < 0) {
            throw new InvalidBidAmountException("Bid amount is less than the minimum required bid amount.");
        }

        // 입찰 금액 업데이트
        foundBid.update(newAmount);
        Bid updatedBid = bidRepository.save(foundBid);
        return BidResponse.of(updatedBid);
    }

    @Override
    public Boolean hasUserAlreadyBid(Long auctionId) {
        List<Bid> bids = bidRepository.findByAuctionId(auctionId);
        return !bids.isEmpty();
    }
}
