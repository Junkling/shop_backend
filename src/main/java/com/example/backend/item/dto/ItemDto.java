package com.example.backend.item.dto;

import com.example.backend.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String name;
    private String imgPath;
    private Integer price;
    private Integer discountPer;

    public void entityToDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.imgPath = item.getImgPath();
        this.price = item.getPrice();
        this.discountPer = item.getDiscountPer();
    }


    public List<ItemDto> toDtoList(List<Item> items) {
        List<ItemDto> list = new ArrayList<>();
        if (!items.isEmpty() && items != null) {
            for (Item item : items) {
                ItemDto itemDto = new ItemDto();
                itemDto.entityToDto(item);
                list.add(itemDto);
            }
        }
        return list;
    }
}
