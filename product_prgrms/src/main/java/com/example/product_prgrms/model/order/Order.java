package com.example.product_prgrms.model.order;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Order {

    private long orderId;

    private final Email email;
    private final String address;
    private OrderStatus orderStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<OrderItem> orderItems;

    public Order(long orderId, Email email, String address, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.email = email;
        this.address = address;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.orderItems = orderItems;
    }

    public Order(Email email, String address, List<OrderItem> orderItems) {
        this.email = email;
        this.address = address;
        this.orderItems = orderItems;

        this.orderStatus = OrderStatus.PAYMENT_CONFIRMED;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void addItems(List<OrderItem> items) {
        for(OrderItem item : items){
            orderItems.add(item);
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void generateKey(long orderId){
        this.orderId = orderId;
    }

    public OrderListDTO toListDTO() {
        return new OrderListDTO(orderId, email.toString(), orderStatus.toString());
    }

    public OrderDTO toDTO() {
        return new OrderDTO(orderId, email.toString(), orderStatus.toString(), createdAt, updatedAt, orderItems);
    }
}
