package com.tasksprints.auction.domain.product.model;

import com.tasksprints.auction.common.entity.BaseEntity;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name="products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToOne
    //mappedby
    private Auction auction;

    @OneToMany
    @Builder.Default
    private List<ProductImage> productImageList = new ArrayList<>();

    public void addOwner(User owner){
        this.owner = owner;
    }
    public void addAuction(Auction auction){
        this.auction = auction;
    }

    public void initProductImageList(List<ProductImage> productImageList){
        this.productImageList = productImageList;
    }
    public void addOwnerAndAuction(User owner, Auction auction){
        addOwner(owner);
        addAuction(auction);
    }

    public static Product create(String name, String description, User owner, Auction auction, List<ProductImage> productImageList){
        Product product = Product.builder()
                .name(name)
                .description(description)
                .build();
        product.addOwnerAndAuction(owner, auction);
        product.initProductImageList(productImageList);
        return product;
    }

    public void update(String name, String description){
        this.name = name;
        this.description = description;
    }

}
