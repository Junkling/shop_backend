package com.example.backend.order.entity;

import com.example.backend.order.dto.OrderSheetDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_sheets")
public class OrderSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long memberId;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(length = 10, nullable = false)
    private String payment;

    @Column(length = 16)
    private String cardNumber;

    @Column(length = 500, nullable = false)
    private String items;

//    @Column(name = "item_ids")
//    private List<Long> itemIds;


    public OrderSheet(Long memberId, String name, String address, String payment, String cardNumber, String items) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.payment = payment;
        this.cardNumber = cardNumber;
        this.items = items;
    }
    public OrderSheetDto toDto() {
        return new OrderSheetDto(this.id, this.name, this.address, this.payment, this.cardNumber, this.items);
    }
}
