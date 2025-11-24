package com.ssg.gallery.item.entity;

import com.ssg.gallery.item.dto.ItemRead;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// 엔터티는 JPA 컨테이너에서 관리됨
// 스프링 컨테이너에서는 JPA 컨테이너에서 엔터티를 꺼내서 사용
@Getter
@Entity
@Table(name="items")    // 매핑된 데이터베이스 테이블(gallery 스키마의 item 테이블)을 지정
public class Item {
    
    // @Id: 테이블의 기본키 값을 저장하는 필드임을 명시 - 엔티티 생성 시 필수요소
    // @Id가 붙은 필드는 테이블의 기본키 컬럼과 매핑됨
    // @GeneratedValue: 기본키값 부여 시 auto_increment를 적용 + 기본키 생성전략을 GenerationType.IDENTITY로 지정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String imgPath;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer discountPer;

    // 생성일시 필드, 널 미허용, 최초 입력 이후 수정 불가
    @Column(updatable = false, nullable = false)
    @CreationTimestamp      // 데이터 추가 시 지정한 값이 없다면 현재 시간을 입력
    private LocalDateTime created;
    
    
    // 상품서비스에서의 데이터 조회용 DTO 변환
    // 엔티티 객체를 상품조회 DTO로 변환하는 메서드 -> Builder를 활용하여 필드값 초기화와 DTO 객체 생성을 간단하게 수행 가능
    public ItemRead toRead() {
        return ItemRead.builder()
                .id(this.id)
                .name(this.name)
                .imgPath(this.imgPath)
                .price(this.price)
                .discountPer(this.discountPer)
                .build();
    }
}
