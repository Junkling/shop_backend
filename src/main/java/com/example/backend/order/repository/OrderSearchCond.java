package com.example.backend.order.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSearchCond {
    private Long itemId;
    private Long sellerId;
    private String searchDate;
}
