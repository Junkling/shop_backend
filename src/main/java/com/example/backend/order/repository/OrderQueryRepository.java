package com.example.backend.order.repository;

import com.example.backend.order.entity.Order;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.backend.order.entity.QOrder.order;

@Repository
@Transactional
public class OrderQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public OrderQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Order> getOrderList(OrderSearchCond cond) {
        Long itemId = cond.getItemId();
        String searchDate = cond.getSearchDate();
        List<Order> orders = query
                .select(order)
                .from(order)
                .where(itemIdSame(itemId), orderTimeBefore(searchDate))
                .orderBy(order.id.desc())
                .fetch();
        return orders;
    }

    private BooleanExpression itemIdSame(Long itemId) {
        if (itemId != null) {
            return order.item.id.like(Long.toString(itemId));
        }
        return null;
    }

    private BooleanExpression orderTimeBefore(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        switch (searchDateType) {
            case "1d" : dateTime = dateTime.minusDays(1); break;
            case "1w" : dateTime = dateTime.minusWeeks(1); break;
            case "1m" : dateTime = dateTime.minusMonths(1); break;
            case "6m" : dateTime = dateTime.minusMonths(6); break;
            case "1y" : dateTime = dateTime.minusYears(1); break;
            default: return null;
        }

        return order.orderTime.after(dateTime);
    }


}
