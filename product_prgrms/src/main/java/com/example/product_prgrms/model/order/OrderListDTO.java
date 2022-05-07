package com.example.product_prgrms.model.order;

public class OrderListDTO{
    public long orderId;
    public String email;
    public String orderStatus;

    public OrderListDTO(long orderId, String email, String orderStatus) {
        this.orderId = orderId;
        this.email = email;
        this.orderStatus = orderStatus;
    }
}
