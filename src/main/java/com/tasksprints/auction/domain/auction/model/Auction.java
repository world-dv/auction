package com.tasksprints.auction.domain.auction.model;

import com.tasksprints.auction.common.entity.BaseEntity;
import com.tasksprints.auction.domain.product.model.Product;
import com.tasksprints.auction.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity(name = "auction")
public class Auction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuctionCategory auctionCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private AuctionStatus auctionStatus;

    @Column(nullable = false)
    private BigDecimal startingBid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User seller;

    @OneToOne
    @Builder.Default
    private Product product = null;

    public void addProduct(Product product){
        product.addAuction(this);
        this.product = product;
    }

    public void addUser(User seller){
        seller.addAuction(this);
        this.seller = seller;
    }

    public static Auction create(LocalDateTime startTime, LocalDateTime endTime, BigDecimal startingBid, AuctionCategory auctionCategory, AuctionStatus auctionStatus, User seller){
        Auction newAuction = Auction.builder()
                .startTime(startTime)
                .endTime(endTime)
                .startingBid(startingBid)
                .auctionCategory(auctionCategory)
                .auctionStatus(auctionStatus)
                .build();
        newAuction.addUser(seller);
        return newAuction;
    }

}
