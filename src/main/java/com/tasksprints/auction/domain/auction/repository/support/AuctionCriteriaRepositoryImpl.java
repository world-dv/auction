package com.tasksprints.auction.domain.auction.repository.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;
import com.tasksprints.auction.domain.auction.dto.response.AuctionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tasksprints.auction.domain.auction.model.QAuction.auction;
import static com.tasksprints.auction.domain.product.model.QProduct.product;
import static com.tasksprints.auction.domain.user.model.QUser.user;

@RequiredArgsConstructor
@Repository
public class AuctionCriteriaRepositoryImpl implements AuctionCriteriaRepository {
    private final JPAQueryFactory queryFactory;

    public Page<AuctionResponse> getAuctionsByFilters(Pageable pageable, AuctionRequest.SearchCondition condition) {
        BooleanBuilder builder = buildSearchCondition(condition);

        var query = queryFactory
                .select(Projections.constructor(AuctionResponse.class,
                        auction.id,
                        auction.startTime,
                        auction.endTime,
                        auction.auctionCategory.stringValue(),
                        auction.auctionStatus.stringValue(),
                        auction.startingBid,
                        auction.viewCount,
                        auction.seller.id.as("sellerId"),
                        auction.seller.nickName.as("sellerNickName")
                ))
                .from(auction)
                .leftJoin(auction.product, product)
                .leftJoin(auction.seller, user)
                .where(builder);

        OrderSpecifier<?> sortOrder = getSortOrder(condition);
        if (sortOrder != null) {
            query = query.orderBy(sortOrder);
        }
        // 페이징
        query = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<AuctionResponse> result = query.fetch();

        //int 오버플로 주의
        int total = queryFactory
            .selectFrom(auction)
            .where(builder)
            .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }


    private BooleanBuilder buildSearchCondition(AuctionRequest.SearchCondition condition) {
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

    private OrderSpecifier<?> getSortOrder(AuctionRequest.SearchCondition condition) {
        if (condition.getSortBy() != null) {
            return switch (condition.getSortBy()) {
                case "bidsAsc" -> auction.bids.size().asc();
                case "bidsDesc" -> auction.bids.size().desc();
                case "endTimeASC" -> auction.endTime.asc();
                case "startTimeASC" -> auction.startTime.asc();
                case "viewCountDESC" -> auction.viewCount.desc();

                default -> null;
            };
        }
        return null;
    }


}
