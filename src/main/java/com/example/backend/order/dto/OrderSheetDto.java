package com.example.backend.order.dto;

import com.example.backend.order.entity.OrderSheet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSheetDto {
    private Long id;
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String items;

    public List<OrderSheetDto> toDtoList(List<OrderSheet> orders) {
        List<OrderSheetDto> list = new ArrayList<>();
        if (!orders.isEmpty() && orders != null) {
            for (OrderSheet order : orders) {
                OrderSheetDto orderDto = order.toDto();
                list.add(orderDto);
            }
        }
        return list;
    }
}
