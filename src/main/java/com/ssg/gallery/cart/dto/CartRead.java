package com.ssg.gallery.cart.dto;

import lombok.Builder;
import lombok.Getter;

// 장바구니 조회용 DTO
@Getter
@Builder
public class CartRead {

    private Integer id;
    private Integer itemId;
}
