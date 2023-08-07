package com.example.backend.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private String name;
    private String imgPath;
    private Integer price;
    private Integer discountPer;
    private Integer quantity;
}
