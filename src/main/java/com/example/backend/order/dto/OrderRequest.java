package com.example.backend.order.dto;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class OrderRequest {
    @NotNull
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String items;

}
