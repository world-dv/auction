package com.tasksprints.auction.domain.review.model;

import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private Integer rating;  // 리뷰 평점 (1~5 등으로 제한할 수 있음)

    @Column(unique = true)
    private String content;  // 리뷰 내용

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "auction_id")
    private Auction auction;
    public void addWriter(User writer) {
        this.writer = writer;
    }
    public void addAuction(Auction auction){
        this.auction = auction;
    }
    public void addWriterAndAuction(User writer, Auction auction){
        addWriter(writer);
        addAuction(auction);
    }


    public static  Review create(String content, Integer rating,User writer, Auction auction) {
        Review newReview = Review.builder()
                .content(content)
                .rating(rating)
                .build();

        newReview.addWriterAndAuction(writer, auction);
        return newReview;
    }
}
