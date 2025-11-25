package com.ssg.gallery.order.service;

import com.ssg.gallery.order.dto.OrderRead;
import com.ssg.gallery.order.dto.OrderRequest;

import java.util.List;

public interface OrderService {

    // 로그인한 사용자의 전체 주문 목록 조회
    List<OrderRead> findAll(Integer memberId);

    // 로그인한 사용자의 특정 주문 상세 조회
    OrderRead find(Integer id, Integer memberId);

    // 현재 로그인한 사용자의 새로운 주문 내역을 저장
    void order(OrderRequest orderReq, Integer memberId);
}
