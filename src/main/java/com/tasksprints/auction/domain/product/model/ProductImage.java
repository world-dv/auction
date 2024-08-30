package com.tasksprints.auction.domain.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "product_images")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false)
    @Getter
    private String imageUrl;



//    @ColumnDefault("false")
//    private Boolean isPrime;

    public static ProductImage create(String imageUrl){
        return ProductImage.builder()
                .imageUrl(imageUrl)
                .build();
    }

}
