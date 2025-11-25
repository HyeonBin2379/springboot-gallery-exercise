package com.ssg.gallery.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orderItems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;         // 아이디

    @Column(nullable = false)
    private Integer orderId;    // 주문 아이디

    @Column(nullable = false)
    private Integer itemId;     //  주문할 상품 아이디

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;  // 생성 일시

    // 주문 상품 엔터티 생성
    public OrderItem() {
    }

    public OrderItem(Integer orderId, Integer itemId) {
        this.orderId = orderId;
        this.itemId = itemId;
    }
}
