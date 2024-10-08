package com.tasksprints.auction.domain.socket.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatter is a Querydsl query type for Chatter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatter extends EntityPathBase<Chatter> {

    private static final long serialVersionUID = 1660492673L;

    public static final QChatter chatter = new QChatter("chatter");

    public final StringPath chatterId = createString("chatterId");

    public final StringPath sessionId = createString("sessionId");

    public QChatter(String variable) {
        super(Chatter.class, forVariable(variable));
    }

    public QChatter(Path<? extends Chatter> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatter(PathMetadata metadata) {
        super(Chatter.class, metadata);
    }

}

