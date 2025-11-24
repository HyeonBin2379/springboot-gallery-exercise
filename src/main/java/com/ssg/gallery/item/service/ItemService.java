package com.ssg.gallery.item.service;

import com.ssg.gallery.item.dto.ItemRead;

import java.util.List;

public interface ItemService {

    // 전체 상품 목록 조회 -> 리턴 타입: 상품 조회 DTO 리스트
    List<ItemRead> findAll();

    // 특정 상품 목록 조회(특정 ID 리스트에 포함된 아이디를 갖는 상품만 조회)
    // -> 매개변수로 상품ID 리스트를 받아, 해당 아이디의 상품정보 조회
    List<ItemRead> findAll(List<Integer> ids);
}
