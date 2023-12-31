package com.example.backend.order.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotNull
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String items;
    private List<Long> itemIds;

}
