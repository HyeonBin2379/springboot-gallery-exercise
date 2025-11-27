package com.ssg.gallery.order.service;

import com.ssg.gallery.cart.service.CartService;
import com.ssg.gallery.item.dto.ItemRead;
import com.ssg.gallery.item.service.ItemService;
import com.ssg.gallery.order.dto.OrderRead;
import com.ssg.gallery.order.dto.OrderRequest;
import com.ssg.gallery.order.entity.Order;
import com.ssg.gallery.order.entity.OrderItem;
import com.ssg.gallery.order.repository.OrderRepository;
import com.ssg.gallery.util.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseOrderService implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final ItemService itemService;
    private final CartService cartService;

    // 현재 로그인한 사용자의 전체 주문 목록 조회
    @Override
    public List<OrderRead> findAll(Integer memberId) {
        return orderRepository.findAllByMemberIdOrderByIdDesc(memberId).stream()
                .map(Order::toRead)
                .toList();
    }

    // 현재 로그인한 사용자의 특정 주문 상세 조회
    @Override
    public OrderRead find(Integer id, Integer memberId) {
        Optional<Order> orderOptional = orderRepository.findByIdAndMemberId(id, memberId);

        if (orderOptional.isPresent()) {
            OrderRead order = orderOptional.get().toRead();

            List<OrderItem> orderItems = orderItemService.findAll(order.getId());
            List<Integer> orderItemIds = orderItems.stream().map(OrderItem::getItemId).toList();
            List<ItemRead> items = itemService.findAll(orderItemIds);
            order.setItems(items);

            return order;
        }
        return null;
    }

    // 현재 로그인한 사용자의 주문 요청을 처리
    // 주문한 상품의 총액 계산 + 주문한 상품을 주문 내용에 저장 + 주문 내용을 주문 목록에 저장
    @Override
    public void order(OrderRequest orderReq, Integer memberId) {
        // 1. 주문 요청한 상품(들)을 조회
        List<ItemRead> items = itemService.findAll(orderReq.getItemIds());

        // 2. 주문 내역에 포함된 상품들의 최종 결제 금액 계산 -> 주문 요청 DTO에 추가
        long amount = items.stream()
                .map(item -> item.getPrice() - item.getPrice().longValue()*item.getDiscountPer()/100)
                .reduce(0L, Long::sum);
        orderReq.setAmount(amount);

        // 3. 결제 수단이 카드일 때, 카드번호를 암호화하여 저장
        if ("card".equals(orderReq.getPayment())) {
            orderReq.setCardNumber(EncryptionUtils.encrypt(orderReq.getCardNumber()));
        }

        // 4. 새로운 주문 내역을 현재 사용자의 주문 목록에 추가
        Order order = orderRepository.save(orderReq.toEntity(memberId));

        // 5. 주문 요청할 상품 목록을 주문 상품 목록에 추가
        List<OrderItem> newOrderItems = new ArrayList<>();
        orderReq.getItemIds().forEach((itemId) -> {
            OrderItem newOrderItem = new OrderItem(order.getId(), itemId);
            newOrderItems.add(newOrderItem);
        });
        orderItemService.saveAll(newOrderItems);

        // 6. 현재 회원의 장바구니에서 주문 처리가 완료된 상품을 장바구니에서 삭제
        newOrderItems.forEach(orderItem -> cartService.remove(order.getMemberId(), orderItem.getItemId()));
    }
}
