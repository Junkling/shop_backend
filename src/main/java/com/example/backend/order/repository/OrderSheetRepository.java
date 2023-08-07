package com.example.backend.order.repository;

import com.example.backend.order.entity.OrderSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderSheetRepository extends JpaRepository<OrderSheet, Long> {
    List<OrderSheet> findByMemberIdOrderByIdDesc(Long memberId);
}
