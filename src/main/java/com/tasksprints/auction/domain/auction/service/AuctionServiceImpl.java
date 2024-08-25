package com.tasksprints.auction.domain.auction.service;

import com.tasksprints.auction.domain.auction.dto.response.AuctionResponse;
import com.tasksprints.auction.domain.auction.exception.AuctionAlreadyClosedException;
import com.tasksprints.auction.domain.auction.exception.AuctionNotFoundException;
import com.tasksprints.auction.domain.auction.exception.InvalidAuctionTimeException;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.repository.AuctionRepository;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;
import com.tasksprints.auction.domain.user.exception.UserNotFoundException;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    @Override
    public AuctionResponse createAuction(Long userId, AuctionRequest.Create auctionRequest) {
        User seller = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (auctionRequest.getStartTime().isAfter(auctionRequest.getEndTime())) {
            throw new InvalidAuctionTimeException("End time must be after start time");
        }

        Auction newAuction = Auction.create(
                auctionRequest.getStartTime(),
                auctionRequest.getEndTime(),
                auctionRequest.getStartingBid(),
                auctionRequest.getAuctionCategory(),
                auctionRequest.getAuctionStatus(),
                seller
        );

        Auction savedAuction = auctionRepository.save(newAuction);
        /**
         * Product 생성에 대한 부분 고려 필요
         * STEP 1
         * - S3 버킷에 올리는 api 따로 구성 ( 독립적 시행 ) url 반환
         * - 해당 url 을 토대로 Auction 생성 시, Product 도 같이 생성
         * STEP 2
         * - 각각의 기능을 완전 분리
         */
        return AuctionResponse.of(savedAuction);
    }

    @Override
    public void closeAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException("Auction not found"));

        if (auction.getAuctionStatus() == AuctionStatus.CLOSED) {
            throw new AuctionAlreadyClosedException("Auction is already closed");
        }
        auction.setAuctionStatus(AuctionStatus.CLOSED);
    }

    @Override
    public String getAuctionStatus(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException("Auction not found"));

        return auction.getAuctionStatus().name();
    }

    @Override
    public List<AuctionResponse> getAuctionsByUser(Long userId) {
        User seller = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Auction> foundAuctions =  auctionRepository.findAuctionsByUserId(seller.getId());

        return foundAuctions.stream()
                .map(AuctionResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuctionResponse> getAllAuctions() {
        List<Auction> foundAuctions =  auctionRepository.findAll();
        return foundAuctions.stream()
                .map(AuctionResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public AuctionResponse getAuctionById(Long auctionId) {
        Auction foundAuction =  auctionRepository.findAuctionById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException("Auction not found"));

        return AuctionResponse.of(foundAuction);
    }
}