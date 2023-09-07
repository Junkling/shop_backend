package com.example.backend.order.dto;

import com.example.backend.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private Long memberId;
    private Long sellerId;
    private Long itemId;

    public List<OrderResponse> toDtoList(List<Order> orders) {
        ArrayList<OrderResponse> list = new ArrayList<>();
        for (Order order : orders) {
            list.add(order.toResponse());
        }
        return list;
    }
}
