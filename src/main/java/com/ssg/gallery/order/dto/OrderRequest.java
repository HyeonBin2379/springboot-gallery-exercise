package com.ssg.gallery.order.dto;

import com.ssg.gallery.order.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

    // 로그인한 사용자가 상품 주문 폼에서 입력할 내용
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    // 주문 하나당 여러 개의 상품 주문 가능
    private List<Integer> itemIds;

    // 여기서부터는 서비스 로직을 수행할 때 별도로 추가되는 내용
    // 주문한 상품 목록에 관한 정보를 요구하는 필드 -> 서비스 계층에서 계산된 총액을 저장
    private Long amount;


    // 주문 요청 DTO는 주문에 관한 엔터티로 변환
    public Order toEntity(Integer memberId) {
        return new Order(memberId, this.name, this.address, this.payment, this.cardNumber, this.amount);
    }
}
