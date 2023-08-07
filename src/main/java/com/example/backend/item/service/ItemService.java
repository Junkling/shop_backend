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

    public ItemDto findById(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        return item.toDto();
    }

    public void update(Long itemId, ItemRequest req) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        item.updateEntity(req);

    }
    public List<ItemDto> findByItemIds(List<Long> ids) {
        List<Item> byIdIn = itemRepository.findByIdIn(ids);
        return new ItemDto().toDtoList(byIdIn);
    }

    public ItemDto save(ItemRequest req, Long sellerId) {
        Item item = new Item(req, sellerId);
        itemRepository.save(item);
        return item.toDto();
    }

    public void delete(Long itemId) {
        itemRepository.delete(itemRepository.findById(itemId).orElseThrow());
    }

    public void orderItem(List<Long> itemIds) {
        List<Item> byIdIn = itemRepository.findByIdIn(itemIds);
        for (Item item : byIdIn) {
            item.orderItem();
            itemRepository.save(item);
        }
    }
}
