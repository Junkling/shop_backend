package com.example.backend.item.controller;

import com.example.backend.attachment.entity.Attachment;
import com.example.backend.attachment.service.FileStore;
import com.example.backend.exception.CustomException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.item.dto.ItemDto;
import com.example.backend.item.dto.ItemRequest;
import com.example.backend.item.entity.Item;
import com.example.backend.item.service.ItemService;
import com.example.backend.member.adapter.MemberAdapter;
import com.example.backend.member.dto.MemberDto;
import com.example.backend.member.service.JwtService;
import com.example.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;
    private final MemberService memberService;
    private final JwtService jwtService;
    private final FileStore fileStore;

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

    @GetMapping("/api/seller/items")
    public List<ItemDto> getSellerItems(@CookieValue(name = "token") String token) {
        Long id = jwtService.getId(token);
        return itemService.findByMemberId(id);
    }

    @Transactional
    @PostMapping("/api/seller/items")
    public ResponseEntity saveItem(@RequestPart ItemRequest itemRequest, @RequestPart(value = "image", required = false) MultipartFile image, @CookieValue(value = "token", required = false) String token) throws IOException {
        MemberDto dto = memberService.findByEmail(jwtService.getClaims(token).getSubject());
        dto.changeAuth(token);
        Item save = itemService.save(itemRequest, dto.getId());
        Attachment attachment = fileStore.storeFile(image, save);
        fileStore.save(attachment);
        return new ResponseEntity(save.getId(), HttpStatus.OK);
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
