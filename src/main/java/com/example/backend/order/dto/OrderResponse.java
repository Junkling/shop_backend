package com.example.backend.order.dto;

import com.example.backend.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String items;

    public void entityToDto(Order order) {
        this.id = order.getId();
        this.name = order.getName();
        this.address = order.getAddress();
        this.payment = order.getPayment();
        this.cardNumber = order.getCardNumber();
        this.items = order.getItems();
    }


    public List<OrderResponse> toDtoList(List<Order> orders) {
        List<OrderResponse> list = new ArrayList<>();
        if (!orders.isEmpty() && orders != null) {
            for (Order order : orders) {
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.entityToDto(order);
                list.add(orderResponse);
            }
        }
        return list;
    }
}
