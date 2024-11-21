package com.tasksprints.auction.domain.socket.model;

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

    private static final long serialVersionUID = -65337957L;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final StringPath chatRoomId = createString("chatRoomId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<com.tasksprints.auction.domain.user.model.User, com.tasksprints.auction.domain.user.model.QUser> users = this.<com.tasksprints.auction.domain.user.model.User, com.tasksprints.auction.domain.user.model.QUser>createList("users", com.tasksprints.auction.domain.user.model.User.class, com.tasksprints.auction.domain.user.model.QUser.class, PathInits.DIRECT2);

    public QChatRoom(String variable) {
        super(ChatRoom.class, forVariable(variable));
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoom(PathMetadata metadata) {
        super(ChatRoom.class, metadata);
    }

}

