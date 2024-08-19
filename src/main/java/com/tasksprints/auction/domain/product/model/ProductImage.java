package com.tasksprints.auction.domain.product.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "product_images")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false)
    private String imageUrl;

    @ColumnDefault("false")
    private Boolean isPrime;

}
