package com.tasksprints.auction.domain.auction.repository.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;
import com.tasksprints.auction.domain.auction.model.Auction;
import com.tasksprints.auction.domain.auction.model.QAuction;
import com.tasksprints.auction.domain.product.model.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class AuctionCriteriaRepositoryImpl implements AuctionCriteriaRepository {
    private final JPAQueryFactory queryFactory;

    public List<Auction> getAuctionsByFilters(AuctionRequest.SearchCondition condition) {
        QAuction auction = QAuction.auction;
        QProduct product = QProduct.product;

        BooleanBuilder builder = buildSearchCondition(condition, auction, product);

        var query = queryFactory.selectFrom(auction)
            .leftJoin(auction.product, product)
            .where(builder);

        OrderSpecifier<?> sortOrder = getSortOrder(condition, auction);
        if (sortOrder != null) {
            query = query.orderBy(sortOrder);
        }

        return query.fetch();
    }

    private BooleanBuilder buildSearchCondition(AuctionRequest.SearchCondition condition, QAuction auction, QProduct product) {
        BooleanBuilder builder = new BooleanBuilder();

        if (condition.getAuctionCategory() != null) {
            builder.and(auction.auctionCategory.eq(condition.getAuctionCategory()));
        }
        if (condition.getProductCategory() != null) {
            builder.and(product.category.eq(condition.getProductCategory()));
        }
        if (condition.getMinPrice() != null && condition.getMaxPrice() != null) {
            builder.and(auction.startingBid.between(condition.getMinPrice(), condition.getMaxPrice()));
        }
        if (condition.getStartTime() != null && condition.getEndTime() != null) {
            builder.and(auction.endTime.between(condition.getStartTime(), condition.getEndTime()));
        }
        if (condition.getAuctionStatus() != null) {
            builder.and(auction.auctionStatus.eq(condition.getAuctionStatus()));
        }

        return builder;
    }

    private OrderSpecifier<?> getSortOrder(AuctionRequest.SearchCondition condition, QAuction auction) {
        if (condition.getSortBy() != null) {
            return switch (condition.getSortBy()) {
                case "bidsAsc" -> auction.bids.size().asc();
                case "bidsDesc" -> auction.bids.size().desc();
                case "endingSoon" -> auction.endTime.asc();
                default -> null;
            };
        }
        return null;
    }
}
