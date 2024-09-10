package com.tasksprints.auction.domain.auction.repository.support;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.model.AuctionCategory;
import com.tasksprints.auction.domain.auction.model.AuctionStatus;
import com.tasksprints.auction.domain.auction.model.QAuction;
import com.tasksprints.auction.domain.product.model.ProductCategory;
import com.tasksprints.auction.domain.product.model.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AuctionCriteriaRepositoryImpl implements AuctionCriteriaRepository {
    private final JPAQueryFactory queryFactory;

    public List<Auction> getAuctionsByFilters(ProductCategory productCategory,
                                              AuctionCategory category,
                                              LocalDateTime now,
                                              LocalDateTime endTime,
                                              AuctionStatus status
    ) {
        QAuction auction = QAuction.auction;
        QProduct product = QProduct.product;
        return queryFactory.selectFrom(auction)
            .leftJoin(auction.product, product) // Auction과 Product 조인
            .where(category != null ? auction.auctionCategory.eq(category) : null)
            .where(productCategory != null ? product.category.eq(productCategory) : null) // ProductCategory 필터
            .where((now != null && endTime != null) ? auction.endTime.between(now, endTime) : null)
            .where(status != null ? auction.auctionStatus.eq(status) : null)
            // orderBy는 성능 이슈로 일단 보류
//            .orderBy(auction.endTime.asc())
            .fetch();
    }


    @Deprecated
    @Override
    public List<Auction> getAuctionsEndWith24Hours(LocalDateTime now, LocalDateTime next24Hours, AuctionStatus auctionStatus) {
        QAuction auction = QAuction.auction;

        BooleanExpression endTimeBetween = auction.endTime.between(now, next24Hours);
        BooleanExpression statusEquals = auction.auctionStatus.eq(auctionStatus);

        return queryFactory
                .selectFrom(auction)
                .where(endTimeBetween.and(statusEquals))
                .orderBy(auction.endTime.asc())
                .fetch();
    }


}
