package com.tasksprints.auction.domain.auction.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuction is a Querydsl query type for Auction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuction extends EntityPathBase<Auction> {

    private static final long serialVersionUID = -698942437L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuction auction = new QAuction("auction");

    public final com.tasksprints.auction.common.entity.QBaseEntity _super = new com.tasksprints.auction.common.entity.QBaseEntity(this);

    public final EnumPath<AuctionCategory> auctionCategory = createEnum("auctionCategory", AuctionCategory.class);

    public final EnumPath<AuctionStatus> auctionStatus = createEnum("auctionStatus", AuctionStatus.class);

    public final ListPath<com.tasksprints.auction.domain.bid.model.Bid, com.tasksprints.auction.domain.bid.model.QBid> bids = this.<com.tasksprints.auction.domain.bid.model.Bid, com.tasksprints.auction.domain.bid.model.QBid>createList("bids", com.tasksprints.auction.domain.bid.model.Bid.class, com.tasksprints.auction.domain.bid.model.QBid.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.tasksprints.auction.domain.product.model.QProduct product;

    public final com.tasksprints.auction.domain.user.model.QUser seller;

    public final NumberPath<java.math.BigDecimal> startingBid = createNumber("startingBid", java.math.BigDecimal.class);

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QAuction(String variable) {
        this(Auction.class, forVariable(variable), INITS);
    }

    public QAuction(Path<? extends Auction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuction(PathMetadata metadata, PathInits inits) {
        this(Auction.class, metadata, inits);
    }

    public QAuction(Class<? extends Auction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.tasksprints.auction.domain.product.model.QProduct(forProperty("product"), inits.get("product")) : null;
        this.seller = inits.isInitialized("seller") ? new com.tasksprints.auction.domain.user.model.QUser(forProperty("seller")) : null;
    }

}

