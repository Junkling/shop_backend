package com.example.backend.cart.controller;

import com.example.backend.cart.dto.CartDto;
import com.example.backend.cart.service.CartService;
import com.example.backend.item.dto.ItemDto;
import com.example.backend.item.service.ItemService;
import com.example.backend.member.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final ItemService itemService;
    private final JwtService jwtService;
    private final CartService cartService;

//    private void valid(String token) {
//        if (!jwtService.isValid(token)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//    }

    @PostMapping("/api/cart/items/{itemId}")
    public ResponseEntity<CartDto> pushCartItem(@PathVariable Long itemId, @CookieValue(value = "token", required = false) String token) {
        jwtService.isValid(token);
        Long memberId = jwtService.getId(token);
        CartDto cart = cartService.findByMemberIdAndItemId(memberId, itemId);
//        List<ItemDto> items = itemService.findAll();
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/api/cart/items")
    public ResponseEntity<List<ItemDto>> getCartItems(@CookieValue(value = "token", required = false) String token) {
        jwtService.isValid(token);

        Long memberId = jwtService.getId(token);
        List<CartDto> list = cartService.findByMemberId(memberId);
        List<Long> itemIds = list.stream().map(CartDto::getItemId).toList();
        log.info("itemIds ={}", itemIds);

        List<ItemDto> items = itemService.findByItemIds(itemIds);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity deleteItem(@PathVariable Long itemId, @CookieValue(value = "token", required = false)String token) {
        jwtService.isValid(token);
        cartService.deleteCart(jwtService.getId(token), itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
