package com.example.product_prgrms.model.order;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO{

    public long orderId;
    public String email;
    public String orderStatus;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public List<OrderItem> items;


    public OrderDTO(long orderId, String email, String orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt, List<OrderItem> items) {
        this.orderId = orderId;
        this.email = email;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.items = items;
    }
}
