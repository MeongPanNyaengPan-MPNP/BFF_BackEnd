package com.mpnp.baechelin.api.repository;

import com.mpnp.baechelin.store.domain.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static com.mpnp.baechelin.store.domain.QStore.store;

@Repository
@Transactional
public class MapQueryRepository extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public MapQueryRepository(JPAQueryFactory  queryFactory) {
        super(Store.class);
        this.queryFactory = queryFactory;
    }


    public List<Store> findBetweenLngLat(BigDecimal latStart,
                                         BigDecimal latEnd,
                                         BigDecimal lngStart,
                                         BigDecimal lngEnd,
                                         Pageable pageable) {
        return queryFactory.selectFrom(store)
                .where(store.latitude.goe(latStart),
                        store.latitude.loe(latEnd),
                        store.longitude.goe(lngStart),
                        store.longitude.loe(lngEnd))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }
}
