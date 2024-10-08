package com.tasksprints.auction.domain.user.model;

import com.tasksprints.auction.common.entity.BaseEntityWithUpdate;
import com.tasksprints.auction.domain.auction.model.Auction;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted_at is null")
@Getter
@ToString
@Entity(name = "users")
public class User extends BaseEntityWithUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true) //unique 특성 추가 필요
    private String email;

    @Column(nullable = false) //unique 특성 추가 필요
    private String password;

    @Column(nullable = false)
    private String nickName; //unique 특성 추가 필요

    @Column(nullable = true)
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Auction> auctions = new ArrayList<>();
//    추후 추가
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @Builder.Default
//    private List<Bid> bids = new ArrayList<>();

    /**
     * @descripton static factory pattern을 적용하여, 구현
     */
    public static User create(String name, String email, String password, String nickName) {
        return User.builder()
            .name(name)
            .email(email)
            .password(password)
            .nickName(nickName)
            .build();
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    /**
     * @description 인자 길이가 너무 길어서, 함수단위로 쪼갤지에 대한 고민중
     */


    public void update(String name, String password, String nickName) {
        this.name = name;
        this.password = password;
        this.nickName = nickName;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void addAuction(Auction auction) {
        this.auctions.add(auction);
    }
}
