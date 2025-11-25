package com.ssg.gallery.cart.repository;

import com.ssg.gallery.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    // 특정 회원의 장바구니 전체 목록 조회
    List<Cart> findAllByMemberId(Integer memberId);

    // 특정 회원의 특정 상품에 관한 장바구니 정보 조회
    Optional<Cart> findByMemberIdAndItemId(Integer memberId, Integer itemId);

    // 특정 회원의 장바구니 일괄삭제
    void deleteByMemberId(Integer memberId);

    // 특정 회원의 특정 상품을 장바구니에서 삭제
    void deleteByMemberIdAndItemId(Integer memberId, Integer itemId);
}
