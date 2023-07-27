package com.example.backend.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private Long memberId;

    @Column(length = 50, nullable = false)
    private Long itemId;

    public Cart makeCart(Long memberId, Long itemId) {
        this.memberId = memberId;
        this.itemId = itemId;
        return this;
    }
}
