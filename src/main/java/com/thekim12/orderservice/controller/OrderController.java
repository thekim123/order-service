package com.thekim12.orderservice.controller;

import com.thekim12.orderservice.dto.OrderDto;
import com.thekim12.orderservice.jpa.OrderEntity;
import com.thekim12.orderservice.service.OrderService;
import com.thekim12.orderservice.vo.RequestOrder;
import com.thekim12.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {
    private final Environment env;
    private final OrderService orderService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in Order Service on Port %s",
                env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/order")
    public ResponseEntity<?> createOrder(@RequestBody RequestOrder orderDetails, @PathVariable String userId) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);
        orderService.createOrder(orderDto);

        ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<?> getOrder(@PathVariable String userId) {
        Iterable<OrderEntity> orders = orderService.getAllOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orders.forEach(o -> {
            result.add(new ModelMapper().map(o, ResponseOrder.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
