package com.example.backend.order.service;

import com.example.backend.item.repository.ItemRepository;
import com.example.backend.order.dto.OrderSheetDto;
import com.example.backend.order.dto.OrderRequest;
import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.OrderSheet;
import com.example.backend.order.repository.OrderRepository;
import com.example.backend.order.repository.OrderSheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderSheetRepository orderSheetRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public Long save(Long memberId,OrderRequest req) {
        OrderSheet orderSheet = new OrderSheet(memberId, req.getName(), req.getAddress(), req.getPayment(), req.getCardNumber(), req.getItems());
        OrderSheet save = orderSheetRepository.save(orderSheet);
        List<Long> itemIds = req.getItemIds();
        for (Long itemId : itemIds) {
            orderRepository.save(new Order(memberId, itemRepository.findById(itemId).get().getSellerId(), req));
        }
        return save.getId();
    }

    public List<OrderSheetDto> findByMemberId(Long memberId) {
        List<OrderSheet> list = orderSheetRepository.findByMemberIdOrderByIdDesc(memberId);
        List<OrderSheetDto> dtoList = new OrderSheetDto().toDtoList(list);
        return dtoList;
    }

}
