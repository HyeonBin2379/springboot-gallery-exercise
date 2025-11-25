package com.ssg.gallery.order.dto;

import com.ssg.gallery.item.dto.ItemRead;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderRead {

    private Integer id;
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private Long amount;
    private LocalDateTime created;

    // 주문 엔터티에는 없지만 주문 내역 조회 DTO에 추가된 필드
    // 주문 하나당 주문할 상품은 여러개일 수 있음
    // 주문할 상품(들)의 목록은 장바구니에서 가져옴
    private List<ItemRead> items;
}
