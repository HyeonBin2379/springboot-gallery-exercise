package com.ssg.gallery.order.service;

import com.ssg.gallery.order.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    // 주문 목록 조회
    List<OrderItem> findAll(Integer orderId);

    // 주문한 상품의 데이터를 저장
    void saveAll(List<OrderItem> orderItems);
}
