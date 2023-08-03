package com.example.backend.item.dto;

import lombok.Getter;

@Getter
public class ItemRequest {
    private String name;
    private String imgPath;
    private Integer price;
    private Integer discountPer;
    private Integer quantity;
    private Long memberId;
}
