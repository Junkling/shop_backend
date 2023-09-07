package com.example.backend.item.repository;

import com.example.backend.item.entity.Item;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.backend.item.entity.QItem.item;

@Repository
@Transactional
public class ItemQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public ItemQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Item> findItem(ItemSearchCond cond) {
        String name = cond.getName();
//        String category = cond.getCategory();
        List<Item> items = query
                .select(item)
                .from(item)
                .where(nameLike(name))
                .orderBy(item.id.desc())
                .fetch();
        return items;
    }


    private BooleanExpression nameLike(String name) {
        if (StringUtils.hasText(name)) {
            return item.name.like("%" + name + "%");
        }
        return null;
    }
}
