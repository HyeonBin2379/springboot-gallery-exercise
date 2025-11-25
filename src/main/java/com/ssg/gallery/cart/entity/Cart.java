package com.ssg.gallery.cart.entity;

import com.ssg.gallery.cart.dto.CartRead;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;         // 장바구니 아이디

    @Column(nullable = false)
    private Integer memberId;   // 회원 아이디

    @Column(nullable = false)
    private Integer itemId;     // 상품 아이디

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;  // 생성일시

    public Cart() {
    }

    public Cart(Integer memberId, Integer itemId) {
        this.memberId = memberId;
        this.itemId = itemId;
    }

    // 장바구니 조회용 DTO로 변환
    public CartRead toRead() {
        return CartRead.builder()
                .id(this.id)
                .itemId(this.itemId)
                .build();
    }
}
