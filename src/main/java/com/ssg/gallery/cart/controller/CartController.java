package com.ssg.gallery.cart.controller;

import com.ssg.gallery.account.helper.AccountHelper;
import com.ssg.gallery.cart.dto.CartRead;
import com.ssg.gallery.cart.dto.CartRequest;
import com.ssg.gallery.cart.service.CartService;
import com.ssg.gallery.item.dto.ItemRead;
import com.ssg.gallery.item.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CartController {

    private final CartService cartService;
    private final ItemService itemService;
    private final AccountHelper accountHelper;

    // 현재 로그인한 사용자의 장바구니 목록을 조회 -> 세션 조회 필수
    @GetMapping("/api/cart/items")
    public ResponseEntity<?> readAll(HttpServletRequest req) {
        Integer memberId = accountHelper.getMemberId(req);
        List<CartRead> carts = cartService.findAll(memberId);

        List<Integer> itemIds = carts.stream().map(CartRead::getItemId).toList();
        List<ItemRead> items = itemService.findAll(itemIds);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // 현재 로그인한 회원의 장바구니에 상품 추가
    // 로그인한 회원은 장바구니에 상품을 추가하기 위해 상품 아이디만을 전달하여 요청
    // 세션에 저장된 로그인한 회원의 아이디를 불러와서 작업을 수행 
    @PostMapping("/api/carts")
    public ResponseEntity<?> push(HttpServletRequest req, @RequestBody CartRequest cartReq) {
        Integer memberId = accountHelper.getMemberId(req);
        CartRead cart = cartService.find(memberId, cartReq.getItemId());

        if (cart == null) {
            cartService.save(cartReq.toEntity(memberId));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 장바구니 목록에서 지정한 상품을 삭제
    // 로그인한 사용자는 장바구니에서 삭제할 상품의 아이디를 url 주소에 포함시켜 전달
    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity<?> remove(HttpServletRequest req, @PathVariable("itemId") Integer itemId) {
        Integer memberId = accountHelper.getMemberId(req);

        cartService.remove(memberId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
