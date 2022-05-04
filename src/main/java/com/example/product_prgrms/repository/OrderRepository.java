package com.example.product_prgrms.repository;

import com.example.product_prgrms.exception.OrderInsertException;
import com.example.product_prgrms.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order insert(Order order) throws OrderInsertException;

    List<Order> findAll();

    Optional<Order> findById(long orderId);


}
