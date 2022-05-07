package com.example.product_prgrms.repository;

import com.example.product_prgrms.exception.OrderInsertException;
import com.example.product_prgrms.model.order.Order;
import com.example.product_prgrms.model.order.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order insert(Order order) throws OrderInsertException;

    Order updateOrderItems(Order order, List<OrderItem> items);

    List<Order> findAll();

    Optional<Order> findById(long orderId);

    Optional<Order> findByEmail(String mail);


}
