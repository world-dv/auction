package com.tasksprints.auction.domain.auction.repository.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;
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

    public List<Auction> getAuctionsByFilters(AuctionRequest.SearchCondition condition) {
        QAuction auction = QAuction.auction;
        QProduct product = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();

        // AuctionCategory 필터
        if (condition.getAuctionCategory() != null) {
            builder.and(auction.auctionCategory.eq(condition.getAuctionCategory()));
        }

        // ProductCategory 필터
        if (condition.getProductCategory() != null) {
            builder.and(product.category.eq(condition.getProductCategory()));
        }

        // StartTime ~ EndTime 필터
        if (condition.getStartTime() != null && condition.getEndTime() != null) {
            builder.and(auction.endTime.between(condition.getStartTime(), condition.getEndTime()));
        }

        // AuctionStatus 필터
        if (condition.getAuctionStatus() != null) {
            builder.and(auction.auctionStatus.eq(condition.getAuctionStatus()));
        }

        return queryFactory.selectFrom(auction)
            .leftJoin(auction.product, product) // Auction과 Product 조인
            .where(builder)
            // orderBy는 성능 이슈로 일단 보류
            // .orderBy(auction.endTime.asc())
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
