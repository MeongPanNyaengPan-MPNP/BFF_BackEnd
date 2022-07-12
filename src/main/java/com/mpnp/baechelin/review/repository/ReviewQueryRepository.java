package com.mpnp.baechelin.review.repository;

import com.mpnp.baechelin.review.domain.Review;
import com.mpnp.baechelin.store.domain.QStore;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static com.mpnp.baechelin.config.QuerydslConfiguration.locationBuilder;
import static com.mpnp.baechelin.review.domain.QReview.review1;
import static com.mpnp.baechelin.store.domain.QStore.store;
import static com.querydsl.jpa.JPAExpressions.select;

@Repository
@Transactional
public class ReviewQueryRepository extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;
    private final BooleanBuilder builder = new BooleanBuilder();

    public ReviewQueryRepository(JPAQueryFactory queryFactory) {
        super(Review.class);
        this.queryFactory = queryFactory;
    }

    public List<Review> findRecentReviews(BigDecimal latStart,
                                          BigDecimal latEnd,
                                          BigDecimal lngStart,
                                          BigDecimal lngEnd,
                                          int limit) {
        locationBuilder(latStart, latEnd, lngStart, lngEnd, builder);
        // 위도 경도에 해당하는 가게를 찾음 -> 해당 댓글을 다 가져옴 -> 내림차순 정렬 -> limit
        return queryFactory.selectFrom(review1)
                .innerJoin(review1.storeId, store)
                .on(review1.storeId.id.eq(store.id))
                .where(builder)
                .limit(limit)
                .fetch();
    }



}