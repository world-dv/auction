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

@Entity(name = "products")
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

    @Enumerated(EnumType.STRING)
    private ProductCategory category; //제품의 Category

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToOne
    //mappedby
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @OneToMany
    @Builder.Default
    private List<ProductImage> productImageList = new ArrayList<>();

    public static Product create(String name, String description, User owner, Auction auction, String productCategory, List<ProductImage> productImageList) {
        Product product = Product.builder().name(name).description(description).category(ProductCategory.fromDisplayName(productCategory)).build();
        product.addOwnerAndAuction(owner, auction);
        product.initProductImageList(productImageList);
        return product;
    }

    public void addOwner(User owner) {
        this.owner = owner;
    }

    public void addAuction(Auction auction) {
        //양방향 매핑
        this.auction = auction;
        auction.addProduct(this);
    }

    public void initProductImageList(List<ProductImage> productImageList) {
        this.productImageList = productImageList;
    }

    public void addOwnerAndAuction(User owner, Auction auction) {
        addOwner(owner);
        addAuction(auction);
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
