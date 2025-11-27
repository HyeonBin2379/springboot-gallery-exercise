package com.ssg.gallery.order.entity;

import com.ssg.gallery.order.dto.OrderRead;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;     // 주문 아이디

    @Column(nullable = false)
    private Integer memberId;   // 주문자(회원) 아이디

    @Column(length = 50, nullable = false)
    private String name;        // 주문자명

    @Column(length = 500, nullable = false)
    private String address;     // 배송 주소

    @Column(length = 10, nullable = false)
    private String payment;     // 결제 수단

    @Column(length = 50)
    private String cardNumber;  // 카드 번호(계좌이체를 진행할 수 있으므로 null 허용)

    @Column(nullable = false)
    private Long amount;        //  최종 결제 금액

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;  // 주문 생성 일시

    // 주문 생성
    public Order() {
    }

    public Order(Integer memberId, String name, String address, String payment, String cardNumber, Long amount) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.payment = payment;
        this.cardNumber = cardNumber;
        this.amount = amount;
    }

    // 주문 조회용 DTO로 변환
    public OrderRead toRead() {
        return OrderRead.builder()
                .id(this.id)
                .name(this.name)
                .address(this.address)
                .payment(this.payment)
                .amount(this.amount)
                .created(this.created)
                .build();
    }
}
