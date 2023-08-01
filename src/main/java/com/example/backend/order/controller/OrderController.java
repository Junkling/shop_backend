package com.example.backend.order.controller;

import com.example.backend.cart.service.CartService;
import com.example.backend.member.service.JwtService;
import com.example.backend.order.dto.OrderRequest;
import com.example.backend.order.dto.OrderResponse;
import com.example.backend.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final JwtService jwtService;
    private final OrderService orderService;
    private final CartService cartService;

    @Transactional
    @PostMapping("/api/orders")
    public ResponseEntity pushOrder(@RequestBody OrderRequest req, @CookieValue(value = "token", required = false) String token) {
        log.info("req.getItems = {}",req.getItems());

        jwtService.isValid(token);
        Long orderId = orderService.save(jwtService.getId(token), req);
        cartService.deleteByMemberId(jwtService.getId(token));

        return new ResponseEntity(orderId, HttpStatus.OK);
    }

    @GetMapping("/api/orders")
    public ResponseEntity<List<OrderResponse>> getOrders(@CookieValue(value = "token", required = false)String token) {
        List<OrderResponse> list = orderService.findById(jwtService.getId(token));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
