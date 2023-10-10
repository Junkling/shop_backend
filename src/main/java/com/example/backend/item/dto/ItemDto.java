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
    private Integer quantity;
    private Long memberId;




    public List<ItemDto> toDtoList(List<Item> items) {
        List<ItemDto> list = new ArrayList<>();
        if (!items.isEmpty() && items != null) {
            for (Item item : items) {
                ItemDto itemDto = item.toDto();
                list.add(itemDto);
            }
        }
        return list;
    }

    public void changeImgPath(String imgPath) {

        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", price=" + price +
                ", discountPer=" + discountPer +
                ", quantity=" + quantity +
                ", memberId=" + memberId +
                '}';
    }
}
