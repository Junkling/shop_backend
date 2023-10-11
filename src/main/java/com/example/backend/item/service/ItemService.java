package com.example.backend.item.service;

import com.example.backend.attachment.repository.AttachmentRepository;
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
    public final AttachmentRepository attachmentRepository;

    public List<ItemDto> findAll() {
        List<Item> all = itemRepository.findAll();
        return new ItemDto().toDtoList(all);
    }

    public Item findEntityByItemId(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow();
    }

    public ItemDto findById(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        return item.toDto();
    }

    public void update(Long itemId, ItemRequest req) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        item.updateEntity(req);
        itemRepository.save(item);
    }
    public List<ItemDto> findByItemIds(List<Long> ids) {
        List<Item> byIdIn = itemRepository.findByIdIn(ids);
        return new ItemDto().toDtoList(byIdIn);
    }

    public Item save(ItemRequest req, Long sellerId) {
        Item item = new Item(req, sellerId);
        Item save = itemRepository.save(item);
        return save;
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

    public List<ItemDto> findByMemberId(Long memberId) {
        List<Item> bySellerId = itemRepository.findBySellerId(memberId);
        ItemDto itemDto = new ItemDto();
        return itemDto.toDtoList(bySellerId);
    }
}
