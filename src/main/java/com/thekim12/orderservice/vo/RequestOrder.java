package com.thekim12.orderservice.vo;

import lombok.Data;

@Data
public class RequestOrder {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
}
