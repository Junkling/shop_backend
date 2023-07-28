package com.example.backend.cart.service;

import com.example.backend.cart.dto.CartDto;
import com.example.backend.cart.entity.Cart;
import com.example.backend.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public List<CartDto> findByMemberId(Long memberId) {
        List<Cart> list = cartRepository.findByMemberId(memberId);
        return new CartDto().toDtoList(list);
    }

    public CartDto findByMemberIdAndItemId(Long memberId, Long itemId) {
        CartDto cartDto = new CartDto();
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);
        if(cart==null){
            Cart newCart = new Cart(memberId, itemId);
            cartDto.entityToDto(cartRepository.save(newCart));
            return cartDto;
        }
        cartDto.entityToDto(cart);
        return cartDto;
    }

    public void deleteCart(Long memberId, Long itemId) {
        cartRepository.delete(cartRepository.findByMemberIdAndItemId(memberId, itemId));
    }
}
