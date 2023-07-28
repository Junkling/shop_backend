package com.example.backend.item.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(length = 100)
    private String imgPath;

    @Column
    private Integer price;
    @Column
    private Integer discountPer;

    public Item(String name, String imgPath, Integer price, Integer discountPer) {
        this.name = name;
        this.imgPath = imgPath;
        this.price = price;
        this.discountPer = discountPer;
    }
}
