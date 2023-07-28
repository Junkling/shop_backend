package com.example.backend.order.dto;

import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
public class OrderRequest {
    @NotNull
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String items;

}
