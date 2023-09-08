package com.example.backend.dashboard.controller;

import com.example.backend.dashboard.sevice.DashboardService;
import com.example.backend.item.dto.ItemDto;
import com.example.backend.item.repository.ItemSearchCond;
import com.example.backend.member.service.JwtServiceImpl;
import com.example.backend.order.dto.OrderResponse;
import com.example.backend.order.repository.OrderSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DashBoardController {
    private final DashboardService dashboardService;
    private final JwtServiceImpl jwtService;

    @GetMapping("/api/dashboard/items")
    public List<ItemDto> searchItems(@CookieValue(name = "token") String token, ItemSearchCond cond) {
        cond.changeSellerId(jwtService.getId(token));
        List<ItemDto> items = dashboardService.findBySearchCond(cond);
        return items;
    }

    @GetMapping("/api/dashboard/{itemId}")
    public ResponseEntity<List<OrderResponse>> searchOrders(@CookieValue(name = "token") String token, @PathVariable Long itemId, OrderSearchCond cond) {
        if (dashboardService.validSeller(jwtService.getId(token), itemId)) {
            cond.changeItemId(itemId);
            List<OrderResponse> responses = dashboardService.findOrdersByItemIdAndTime(cond);
            return new ResponseEntity(responses, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/dashboard/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders(@CookieValue(name = "token") String token) {
        List<OrderResponse> allOrders = dashboardService.findAllOrdersBySellerId(jwtService.getId(token));
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

}
