package com.example.product_prgrms.service;

import com.example.product_prgrms.exception.InvalidProductStockException;
import com.example.product_prgrms.exception.OrderInsertException;
import com.example.product_prgrms.model.order.*;
import com.example.product_prgrms.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService)  {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Transactional
    public void createOrder(OrderRequest orderRequest) throws OrderInsertException, InvalidProductStockException {
        for (OrderItem item : orderRequest.items) {
            productService.changeProductStock(item);
        }

        var findOne= orderRepository.findByEmail(orderRequest.email);
        if (findOne.isEmpty()) {
            orderRepository.insert(orderRequest.toEntity());
        } else {
            var updatedOne= findOne.get();
            updatedOne.addItems(orderRequest.items);
            orderRepository.updateOrderItems(updatedOne, orderRequest.items);
        }
    }

    public OrderDTO findOrderById(long id){
        var findOne = orderRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return findOne.toDTO();
    }

    public List<OrderListDTO> findAllOrders(){
        return orderRepository.findAll().stream().map(Order::toListDTO).toList();
    }

}
