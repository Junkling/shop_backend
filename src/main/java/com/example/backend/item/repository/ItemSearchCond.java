package com.example.backend.item.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemSearchCond {
    private String name;
    private Long sellerId;
    private String category;

    public void changeSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}
