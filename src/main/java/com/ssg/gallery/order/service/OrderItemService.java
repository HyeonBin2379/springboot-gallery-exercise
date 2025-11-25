package com.ssg.gallery.order.service;

import com.ssg.gallery.order.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> findAll(Integer orderId);

    void saveAll(List<OrderItem> orderItems);
}
