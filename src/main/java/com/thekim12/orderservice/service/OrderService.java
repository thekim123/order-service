package com.thekim12.orderservice.service;

import com.thekim12.orderservice.dto.OrderDto;
import com.thekim12.orderservice.jpa.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);

    OrderDto getOrdersByOrderId(String orderId);

    Iterable<OrderEntity> getAllOrdersByUserId(String userId);
}
