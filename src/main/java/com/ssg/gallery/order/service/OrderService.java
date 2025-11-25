package com.ssg.gallery.order.service;

import com.ssg.gallery.order.dto.OrderRead;
import com.ssg.gallery.order.dto.OrderRequest;

import java.util.List;

public interface OrderService {

    List<OrderRead> findAll(Integer memberId);
    OrderRead find(Integer id, Integer memberId);
    void order(OrderRequest orderReq, Integer memberId);
}
