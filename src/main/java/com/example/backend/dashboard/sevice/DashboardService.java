package com.example.backend.dashboard.sevice;

import com.example.backend.item.dto.ItemDto;
import com.example.backend.item.repository.ItemQueryRepository;
import com.example.backend.item.repository.ItemRepository;
import com.example.backend.item.repository.ItemSearchCond;
import com.example.backend.order.dto.OrderResponse;
import com.example.backend.order.repository.OrderQueryRepository;
import com.example.backend.order.repository.OrderRepository;
import com.example.backend.order.repository.OrderSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final ItemQueryRepository itemQueryRepository;
    private final OrderQueryRepository orderQueryRepository;

    public List<ItemDto> findBySellerId(Long sellerId) {
        return new ItemDto().toDtoList(itemRepository.findBySellerId(sellerId));
    }

    public List<ItemDto> findBySearchCond(ItemSearchCond cond) {
        return new ItemDto().toDtoList(itemQueryRepository.findItem(cond));
    }

    public List<OrderResponse> findAllOrdersBySellerId(Long itemId) {
        return new OrderResponse().toDtoList(orderRepository.findBySellerId(itemId));
    }

    public List<OrderResponse> findOrdersByItemIdAndTime(OrderSearchCond cond) {
        return new OrderResponse().toDtoList(orderQueryRepository.getOrderList(cond));
    }

    public boolean validSeller(Long sellerId, Long itemId) {
        if(itemRepository.findById(itemId).orElseThrow().getSellerId()==sellerId) return true;
        return false;
    }


}
