package com.example.backend.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "carts")
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private Long memberId;

    @Column(length = 50, nullable = false)
    private Long itemId;

    public Cart(Long memberId, Long itemId) {
        this.memberId = memberId;
        this.itemId = itemId;
    }
}
