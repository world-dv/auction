package com.tasksprints.auction.domain.product.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1668563853L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final com.tasksprints.auction.common.entity.QBaseEntity _super = new com.tasksprints.auction.common.entity.QBaseEntity(this);

    public final com.tasksprints.auction.domain.auction.model.QAuction auction;

    public final EnumPath<ProductCategory> category = createEnum("category", ProductCategory.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final com.tasksprints.auction.domain.user.model.QUser owner;

    public final ListPath<ProductImage, QProductImage> productImageList = this.<ProductImage, QProductImage>createList("productImageList", ProductImage.class, QProductImage.class, PathInits.DIRECT2);

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auction = inits.isInitialized("auction") ? new com.tasksprints.auction.domain.auction.model.QAuction(forProperty("auction"), inits.get("auction")) : null;
        this.owner = inits.isInitialized("owner") ? new com.tasksprints.auction.domain.user.model.QUser(forProperty("owner")) : null;
    }

}

