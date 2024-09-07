package com.tasksprints.auction.domain.auction.repository.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.model.QAuction;
import com.tasksprints.auction.domain.product.model.ProductCategory;
import com.tasksprints.auction.domain.product.model.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class AuctionCriteriaRepositoryImpl implements AuctionCriteriaRepository {
    private final JPAQueryFactory queryFactory;

    public List<Auction> getAuctionsByFilters(ProductCategory productCategory,
                                              AuctionCategory category
    ) {
        QAuction auction = QAuction.auction;
        QProduct product = QProduct.product;
        return queryFactory.selectFrom(auction)
            .leftJoin(auction.product, product) // Auction과 Product 조인
            .where(category != null ? auction.auctionCategory.eq(category) : null)
            .where(productCategory != null ? product.category.eq(productCategory) : null) // ProductCategory 필터
            .fetch();
    }


}
