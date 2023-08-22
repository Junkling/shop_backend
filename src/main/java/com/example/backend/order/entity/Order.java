package com.example.backend.order.entity;

import com.example.backend.order.dto.OrderRequest;
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

    public Order(Long memberId, Long sellerId, OrderRequest orderRequest) {
        this.memberId = memberId;
        this.sellerId = sellerId;
        this.name = orderRequest.getName();
        this.address = orderRequest.getAddress();
        this.payment = orderRequest.getPayment();
        this.cardNumber = orderRequest.getCardNumber();
    }
}
