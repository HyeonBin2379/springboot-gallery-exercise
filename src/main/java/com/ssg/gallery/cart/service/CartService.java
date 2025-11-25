package com.ssg.gallery.cart.service;

import com.ssg.gallery.cart.dto.CartRead;
import com.ssg.gallery.cart.entity.Cart;

import java.util.List;

public interface CartService {

    List<CartRead> findAll(Integer memberId);

    CartRead find(Integer memberId, Integer itemId);

    void removeAll(Integer memberId);

    void remove(Integer memberId, Integer itemId);

    // 장바구니 데이터 저장
    void save(Cart cart);
}
