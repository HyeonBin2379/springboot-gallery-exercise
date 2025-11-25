package com.ssg.gallery.cart.entity;

import lombok.Getter;

@Getter
public class CartRequest {

    private Integer itemId;

    public Cart toEntity(Integer memberId) {
        return new Cart(memberId, this.itemId);
    }
}
