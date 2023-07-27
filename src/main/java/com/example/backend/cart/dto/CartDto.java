package com.example.backend.cart.dto;

import com.example.backend.cart.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;

    private Long memberId;

    private Long itemId;

    public void entityToDto(Cart entity) {
        this.id = entity.getId();
        this.memberId = entity.getMemberId();
        this.itemId = entity.getItemId();
    }

    public List<CartDto> toDtoList(List<Cart> carts) {
        ArrayList<CartDto> list = new ArrayList<>();
        if (!carts.isEmpty() && carts != null) {
            for (Cart cart : carts) {
                CartDto cartDto = new CartDto();
                cartDto.entityToDto(cart);
                list.add(cartDto);
            }
        }
        return list;
    }
}
