package com.example.backend.cart.entity;

import com.example.backend.cart.dto.CartDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public CartDto toDto() {
        return new CartDto(this.getId(), this.getMemberId(), this.getItemId());
    }
}
