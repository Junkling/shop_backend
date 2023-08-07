package com.example.backend.item.controller;

import com.example.backend.exception.CustomException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.item.dto.ItemDto;
import com.example.backend.item.dto.ItemRequest;
import com.example.backend.item.service.ItemService;
import com.example.backend.member.adapter.MemberAdapter;
import com.example.backend.member.service.JwtService;
import com.example.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final MemberService memberService;
    private final JwtService jwtService;

    private void checkAuth(String role) {
        if (!role.equals("seller")) throw new CustomException(ErrorCode.INVALID_PERMISSION);
    }

    private void checkSeller(Long memberId, ItemDto dto, String role) {
        checkAuth(role);
        if (memberId != dto.getMemberId()) throw new CustomException(ErrorCode.INVALID_PERMISSION);
    }

    @GetMapping("/api/items")
    public List<ItemDto> getItems() {
        List<ItemDto> items = itemService.findAll();
        return items;
    }

    @Transactional
    @PostMapping("/api/seller/items")
    public ResponseEntity saveItem(@RequestBody ItemRequest req, @AuthenticationPrincipal MemberAdapter memberAdapter) {
        checkAuth(memberAdapter.getRole());
        ItemDto save = itemService.save(req,memberAdapter.getId());
        return new ResponseEntity(save, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/api/seller/items/{itemId}")
    public ResponseEntity updateItem(@PathVariable Long itemId, @RequestBody ItemRequest req, @AuthenticationPrincipal MemberAdapter memberAdapter) {
        checkSeller(memberAdapter.getId(), itemService.findById(itemId), memberAdapter.getRole());
        itemService.update(itemId, req);
        return new ResponseEntity(itemService.findById(itemId), HttpStatus.OK);
    }

    @DeleteMapping("/api/seller/items/{itemId}")
    public ResponseEntity deleteItem(@PathVariable Long itemId, @AuthenticationPrincipal MemberAdapter memberAdapter) {
        checkSeller(memberAdapter.getId(), itemService.findById(itemId), memberAdapter.getRole());
        itemService.delete(itemId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
