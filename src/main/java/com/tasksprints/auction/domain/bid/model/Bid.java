package com.tasksprints.auction.domain.bid.model;

import com.tasksprints.auction.common.entity.BaseEntity;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "bids")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Bid extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    @Builder.Default
    private Auction auction = null;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Builder.Default
    private User user = null;

    public void addUser(User user){
        this.user = user;
        /**
         * 단방향
         */
    }

    public void addAuction(Auction auction){
        this.auction = auction;
    }
    public void addUserAndAuction(User user, Auction auction){
        addUser(user);
        addAuction(auction);
    }
    public static Bid create(BigDecimal amount, User user, Auction auction){
        Bid newBid = Bid.builder()
                .amount(amount)
                .build();

        newBid.addUserAndAuction(user, auction);
        return newBid;
    }

    public void update(BigDecimal amount){
        this.amount = amount;
    }

}
