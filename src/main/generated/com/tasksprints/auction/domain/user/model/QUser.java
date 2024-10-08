package com.tasksprints.auction.domain.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -738054213L;

    public static final QUser user = new QUser("user");

    public final com.tasksprints.auction.common.entity.QBaseEntityWithUpdate _super = new com.tasksprints.auction.common.entity.QBaseEntityWithUpdate(this);

    public final ListPath<com.tasksprints.auction.domain.auction.model.Auction, com.tasksprints.auction.domain.auction.model.QAuction> auctions = this.<com.tasksprints.auction.domain.auction.model.Auction, com.tasksprints.auction.domain.auction.model.QAuction>createList("auctions", com.tasksprints.auction.domain.auction.model.Auction.class, com.tasksprints.auction.domain.auction.model.QAuction.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

