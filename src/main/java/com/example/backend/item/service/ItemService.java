package com.example.backend.item.service;

import com.example.backend.item.dto.ItemDto;
import com.example.backend.item.dto.ItemRequest;
import com.example.backend.item.entity.Item;
import com.example.backend.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<ItemDto> findAll() {
        List<Item> all = itemRepository.findAll();
        return new ItemDto().toDtoList(all);
    }

    public List<ItemDto> findByItemIds(List<Long> ids) {
        List<Item> byIdIn = itemRepository.findByIdIn(ids);
        return new ItemDto().toDtoList(byIdIn);
    }

    public ItemDto save(ItemRequest req) {
        Item item = new Item(req.getName(), req.getImgPath(), req.getPrice(), req.getDiscountPer(), req.getQuantity(), req.getMemberId());
        itemRepository.save(item);
        return item.toDto();
    }
}
