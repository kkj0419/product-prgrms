package com.example.product_prgrms.model.order;

import java.util.List;

public class OrderRequest {

    public String email;
    public String address;

    public List<OrderItem> items;

    public OrderRequest(){}

    public OrderRequest(String email, String address, List<OrderItem> items) {
        this.email = email;
        this.address = address;
        this.items = items;
    }

    public Order toEntity(){
        return new Order(new Email(email), address, items);
    }

}
