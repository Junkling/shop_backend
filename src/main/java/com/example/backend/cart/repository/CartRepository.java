package com.example.backend.cart.repository;

import com.example.backend.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByMemberId(Long memberId);

    Cart findByMemberIdAndItemId(Long memberId, Long itemId);

    void deleteByMemberId(Long memberId);
}
