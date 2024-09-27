package com.tasksprints.auction.domain.auction.repository.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tasksprints.auction.domain.auction.dto.request.AuctionRequest;
import com.tasksprints.auction.domain.auction.dto.response.AuctionResponse;
import com.tasksprints.auction.domain.product.model.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tasksprints.auction.domain.auction.model.QAuction.auction;
import static com.tasksprints.auction.domain.product.model.QProduct.product;
import static com.tasksprints.auction.domain.product.model.QProductImage.productImage;
import static com.tasksprints.auction.domain.user.model.QUser.user;

@RequiredArgsConstructor
@Repository
public class AuctionCriteriaRepositoryImpl implements AuctionCriteriaRepository {
    private final JPAQueryFactory queryFactory;

    public Page<AuctionResponse.Details> getAuctionsByFilters(Pageable pageable, AuctionRequest.SearchCondition condition) {
        BooleanBuilder builder = buildSearchCondition(condition);
        OrderSpecifier<?> sortOrder = getSortOrder(condition);
        List<AuctionResponse.Details> result = buildQueryWithPaginationAndSorting(builder, pageable, sortOrder);

        //int 오버플로 주의
        int total = queryFactory
            .selectFrom(auction)
            .where(builder)
            .fetch().size();

        return new PageImpl<>(result, pageable, total);
    }
    private List<AuctionResponse.Details> buildQueryWithPaginationAndSorting(BooleanBuilder builder, Pageable pageable, OrderSpecifier<?> sortOrder) {
        var mainQuery = queryFactory
            .select(Projections.fields(AuctionResponse.Details.class,
                auction.id,
                auction.startTime,
                auction.endTime,
                auction.auctionCategory.stringValue().as("category"),
                auction.auctionStatus.stringValue().as("status"),
                auction.startingBid,
                auction.viewCount,
                auction.seller.id.as("sellerId"),
                auction.seller.nickName.as("sellerNickName"),
                auction.product.id.as("productId"),
                auction.product.category.stringValue().as("productCategory")
                )
            )
            .from(auction)
            .leftJoin(auction.product, product)
            .leftJoin(auction.seller, user)
            .where(builder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        if (sortOrder != null) {
            mainQuery.orderBy(sortOrder);
        }
        List<AuctionResponse.Details> detailsResult = mainQuery.fetch();
        //컬렉션은 메인 쿼리와 분리
        attachImageUrlsToMainQuery(detailsResult);

        return detailsResult;
    }

    private void attachImageUrlsToMainQuery(List<AuctionResponse.Details> detailsResult) {
        detailsResult.forEach(detail -> {
            List<String> imageUrls = queryFactory
                .select(productImage.imageUrl)
                .from(product)
                .leftJoin(product.productImageList, productImage)
                .where(product.id.eq(detail.getProductId()))
                .fetch();

            detail.setProductImageUrls(imageUrls);
        });
    }

    @Deprecated
    public Page<AuctionResponse.Details> getAuctionsByCategory(Pageable pageable,
                                                       AuctionRequest.SearchCondition condition,
                                                       ProductCategory category) {

        BooleanBuilder builder = buildSearchCondition(condition);
        filterByCategory(category, builder);
        OrderSpecifier<?> sortOrder = getSortOrder(condition);
        List<AuctionResponse.Details> result = buildQueryWithPaginationAndSorting(builder, pageable, sortOrder);

        int total = queryFactory
            .selectFrom(auction)
            .where(builder)
            .fetch().size();

        return new PageImpl<>(result, pageable, total);

    }
    @Deprecated
    private void filterByCategory(ProductCategory category, BooleanBuilder builder) {
        if (category != null) {
            builder.and(product.category.eq(category));
        }
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
