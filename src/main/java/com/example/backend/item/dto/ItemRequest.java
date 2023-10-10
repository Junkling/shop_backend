package com.example.backend.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private String name;
    private List<MultipartFile> imageFiles;
    private Integer price;
    private Integer discountPer;
    private Integer quantity;

    @Override
    public String toString() {
        return "ItemRequest{" +
                "name='" + name + '\'' +
                ", imageFiles=" + imageFiles +
                ", price=" + price +
                ", discountPer=" + discountPer +
                ", quantity=" + quantity +
                '}';
    }
}
