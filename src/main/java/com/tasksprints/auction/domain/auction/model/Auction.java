package com.tasksprints.auction.domain.auction.model;

import com.tasksprints.auction.common.entity.BaseEntity;
import com.tasksprints.auction.domain.bid.model.Bid;
import com.tasksprints.auction.domain.product.model.Product;
import com.tasksprints.auction.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany
    @Builder.Default
    private List<Bid> bids = new ArrayList<>();

    @Column(nullable = false)
    private Long viewCount;

    @PrePersist
        protected void onCreate() {
            if (viewCount == null) {
                viewCount = 0L;  // 기본값 설정
            }
        }

    public static Auction create(LocalDateTime startTime, LocalDateTime endTime, BigDecimal startingBid, AuctionCategory auctionCategory, AuctionStatus auctionStatus, User seller) {
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

    public void addProduct(Product product) {
        //product.addAuction(this); product에서 auction을 추가하고 있어서 중복
        this.product = product;
    }

    public void addUser(User seller) {
        seller.addAuction(this);
        this.seller = seller;
    }

    public void incrementViewCount() {
        if (viewCount == null) {
            viewCount = 0L;
        }
        this.viewCount += 1;
    }


}
