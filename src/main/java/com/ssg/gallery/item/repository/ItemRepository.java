package com.ssg.gallery.item.repository;

import com.ssg.gallery.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository<관리할엔티티, 엔티티기본키타입>를 확장
public interface ItemRepository extends JpaRepository<Item, Integer> {

    // 여러 개의 상품 아이디로 상품 데이터를 조회하는 메서드
    // 쿼리문 작업 불필요
    List<Item> findAllByIdIn(List<Integer> ids);
}
