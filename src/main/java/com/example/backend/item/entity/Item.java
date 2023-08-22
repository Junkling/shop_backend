package com.example.backend.item.entity;

import com.example.backend.attachment.entity.Attachment;
import com.example.backend.item.dto.ItemDto;
import com.example.backend.item.dto.ItemRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Table(name = "items")
@Getter
@Entity
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @OneToOne(
            mappedBy = "item",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Attachment imageFiles;
//    @Column
//    private String imgPath;

    @Column
    private Integer price;
    @Column
    private Integer discountPer;

    @Column(name = "quantity")
    @Max(999)
    @Min(0)
    private Integer quantity;

    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "sell_count")
    private Long sellCount;

    public Item(ItemRequest req, Long sellerId) {
        this.name = req.getName();
        this.price = req.getPrice();
        this.discountPer = req.getDiscountPer();
        this.quantity = req.getQuantity();
        this.sellerId = sellerId;
        this.sellCount = 0L;
    }

    public void updateEntity(ItemRequest req) {
        this.name = req.getName();
        this.price = req.getPrice();
        this.discountPer = req.getDiscountPer();
        this.quantity = req.getQuantity();
    }

    public void orderItem() {
        this.quantity--;
        this.sellCount++;
    }

    public ItemDto toDto() {
        ItemDto itemDto = new ItemDto(this.id, this.name, "/img/"+this.getImageFiles().getStoreName(), this.price, this.discountPer, this.quantity, this.sellerId);
        return itemDto;
    }

}
