package com.ssg.gallery.cart.dto;

import com.ssg.gallery.cart.entity.Cart;
import lombok.Getter;

// 장바구니 담기 요청용 DTO
@Getter
public class CartRequest {

    private Integer itemId;

    public Cart toEntity(Integer memberId) {
        return new Cart(memberId, this.itemId);
    }
}
