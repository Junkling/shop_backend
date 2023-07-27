package com.example.backend.item.controller;

import com.example.backend.item.dto.ItemDto;
import com.example.backend.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/api/items")
    public List<ItemDto> getItems() {
        List<ItemDto> items = itemService.findAll();
        return items;
    }
}
