package com.tasksprints.auction.domain.auction.service;

import com.tasksprints.auction.domain.auction.dto.AuctionDTO;
import com.tasksprints.auction.domain.auction.exception.AuctionAlreadyClosedException;
import com.tasksprints.auction.domain.auction.exception.AuctionNotFoundException;
import com.tasksprints.auction.domain.auction.exception.InvalidAuctionTimeException;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.repository.AuctionRepository;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import com.tasksprints.auction.domain.auction.dto.AuctionRequest;
import com.tasksprints.auction.domain.user.exception.UserNotFoundException;
import com.tasksprints.auction.domain.user.model.User;
import com.tasksprints.auction.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    @Override
    public AuctionDTO createAuction(Long userId, AuctionRequest.Create auctionRequest) {
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

        return AuctionDTO.of(savedAuction);
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
    public List<AuctionDTO> getAuctionsByUser(Long userId) {
        User seller = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Auction> foundAuctions =  auctionRepository.findAuctionsByUserId(seller.getId());

        return foundAuctions.stream()
                .map(AuctionDTO::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuctionDTO> getAllAuctions() {
        List<Auction> foundAuctions =  auctionRepository.findAll();
        return foundAuctions.stream()
                .map(AuctionDTO::of)
                .collect(Collectors.toList());
    }

    @Override
    public AuctionDTO getAuctionById(Long auctionId) {
        Auction foundAuction =  auctionRepository.findAuctionById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException("Auction not found"));

        return AuctionDTO.of(foundAuction);
    }
}