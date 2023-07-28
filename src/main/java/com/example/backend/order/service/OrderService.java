package com.example.backend.order.service;

import com.example.backend.order.dto.OrderRequest;
import com.example.backend.order.dto.OrderResponse;
import com.example.backend.order.entity.Order;
import com.example.backend.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Long save(Long memberId,OrderRequest req) {
        Order order = new Order(memberId, req.getName(), req.getAddress(), req.getPayment(), req.getCardNumber(), req.getItems());
        Order save = orderRepository.save(order);
        return save.getId();
    }

    public List<OrderResponse> findById(Long memberId) {
        List<Order> list = orderRepository.findByMemberId(memberId);
        List<OrderResponse> dtoList = new OrderResponse().toDtoList(list);
        return dtoList;
    }

}
