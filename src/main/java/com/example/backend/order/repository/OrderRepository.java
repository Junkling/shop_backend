package com.example.backend.order.repository;

import com.example.backend.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByItemId(Long itemId);

    List<Order> findBySellerId(Long sellerId);

}
