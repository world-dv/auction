package com.tasksprints.auction.domain.bid.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBid is a Querydsl query type for Bid
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBid extends EntityPathBase<Bid> {

    private static final long serialVersionUID = -531916529L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBid bid = new QBid("bid");

    public final com.tasksprints.auction.common.entity.QBaseEntity _super = new com.tasksprints.auction.common.entity.QBaseEntity(this);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final com.tasksprints.auction.domain.auction.model.QAuction auction;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.tasksprints.auction.domain.user.model.QUser user;

    public final StringPath uuid = createString("uuid");

    public QBid(String variable) {
        this(Bid.class, forVariable(variable), INITS);
    }

    public QBid(Path<? extends Bid> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBid(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBid(PathMetadata metadata, PathInits inits) {
        this(Bid.class, metadata, inits);
    }

    public QBid(Class<? extends Bid> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auction = inits.isInitialized("auction") ? new com.tasksprints.auction.domain.auction.model.QAuction(forProperty("auction"), inits.get("auction")) : null;
        this.user = inits.isInitialized("user") ? new com.tasksprints.auction.domain.user.model.QUser(forProperty("user")) : null;
    }

}

