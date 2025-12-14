package com.ssg.gallery.order.controller;

import com.ssg.gallery.member.entity.LoginUser;
import com.ssg.gallery.order.dto.OrderRead;
import com.ssg.gallery.order.dto.OrderRequest;
import com.ssg.gallery.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class OrderController {

    // 주문 처리를 진행하려면 로그인한 사용자 아이디와 주문 서비스 로직이 필요
    private final OrderService orderService;

    // 로그인한 사용자의 전체 주문 목록 조회
    // 로그인한 사용자 아이디를 찾기 위한 세션 접근 필요
    @GetMapping("/api/orders")
    public ResponseEntity<?> readAll(@AuthenticationPrincipal LoginUser loginUser) {
        Integer memberId = loginUser.getId();

        List<OrderRead> orders = orderService.findAll(memberId);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // 로그인한 사용자의 특정 주문 내역 상세조회
    // 로그인한 사용자 아이디를 찾기 위한 세션 접근 필요
    @GetMapping("/api/orders/{id}")
    public ResponseEntity<?> readAll(@AuthenticationPrincipal LoginUser loginUser, @PathVariable("id") Integer id) {
        Integer memberId = loginUser.getId();

        OrderRead order = orderService.find(id, memberId);

        // 지정한 주문 내역을 찾을 수 없는 경우
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // 현재 로그인한 사용자의 주문 요청을 처리
    // 로그인한 사용자 아이디를 찾기 위한 세션 접근 필요
    // 사용자가 주문 폼에서 입력한 데이터 필요
    @PostMapping("/api/orders")
    public ResponseEntity<?> add(@AuthenticationPrincipal LoginUser loginUser, @RequestBody OrderRequest orderReq) {
        Integer memberId = loginUser.getId();

        orderService.order(orderReq, memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
