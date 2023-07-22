package com.example.backend.item.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "items")
@Getter
@Entity
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


}
