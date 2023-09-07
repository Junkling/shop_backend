package com.example.backend.order.entity;

import com.example.backend.item.entity.Item;
import com.example.backend.order.dto.OrderRequest;
import com.example.backend.order.dto.OrderResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor

@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "seller_id")
    private Long sellerId;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(length = 10, nullable = false)
    private String payment;

    @Column(length = 16)
    private String cardNumber;

    @Column(name = "order_time")
    @CreatedDate
    private LocalDateTime orderTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public Order(Long memberId, Item item, OrderRequest orderRequest) {
        this.memberId = memberId;
        this.sellerId = item.getSellerId();
        this.name = orderRequest.getName();
        this.address = orderRequest.getAddress();
        this.payment = orderRequest.getPayment();
        this.cardNumber = orderRequest.getCardNumber();
        this.item = item;
    }

    public OrderResponse toResponse() {
        OrderResponse orderResponse = new OrderResponse(this.getId(), this.getMemberId(), this.getSellerId(), this.getItem().getId());
        return orderResponse;
    }
}
