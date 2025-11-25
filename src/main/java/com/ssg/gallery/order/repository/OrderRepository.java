package com.ssg.gallery.order.repository;

import com.ssg.gallery.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    // 로그인한 사용자의 전체 주문 내역 조회
    List<Order> findAllByMemberIdOrderByIdDesc(Integer memberId);

    // 로그인한 사용자의 특정 주문 내역 조회
    Optional<Order> findByIdAndMemberId(Integer id, Integer memberId);
}
