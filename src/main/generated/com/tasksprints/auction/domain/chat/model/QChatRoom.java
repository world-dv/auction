package com.tasksprints.auction.domain.chat.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = -1584360170L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final StringPath chatRoomId = createString("chatRoomId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final com.tasksprints.auction.domain.user.model.QUser owner;

    public final ListPath<com.tasksprints.auction.domain.user.model.User, com.tasksprints.auction.domain.user.model.QUser> users = this.<com.tasksprints.auction.domain.user.model.User, com.tasksprints.auction.domain.user.model.QUser>createList("users", com.tasksprints.auction.domain.user.model.User.class, com.tasksprints.auction.domain.user.model.QUser.class, PathInits.DIRECT2);

    public QChatRoom(String variable) {
        this(ChatRoom.class, forVariable(variable), INITS);
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoom(PathMetadata metadata, PathInits inits) {
        this(ChatRoom.class, metadata, inits);
    }

    public QChatRoom(Class<? extends ChatRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.owner = inits.isInitialized("owner") ? new com.tasksprints.auction.domain.user.model.QUser(forProperty("owner")) : null;
    }

}

