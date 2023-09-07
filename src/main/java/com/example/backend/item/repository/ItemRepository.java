package com.example.backend.item.repository;

import com.example.backend.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByIdIn(List<Long> ids);

    List<Item> findBySellerId(Long memberId);

}
