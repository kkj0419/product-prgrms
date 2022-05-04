package com.example.product_prgrms.service;

import com.example.product_prgrms.exception.OrderInsertException;
import com.example.product_prgrms.model.order.OrderDTO;
import com.example.product_prgrms.model.order.OrderItem;
import com.example.product_prgrms.model.order.OrderListDTO;
import com.example.product_prgrms.model.order.OrderRequest;
import com.example.product_prgrms.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService)  {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Transactional
    public void createOrder(OrderRequest orderRequest) throws OrderInsertException {
        for (OrderItem item : orderRequest.items) {
            productService.changeProductStock(item);
        }
        orderRepository.insert(orderRequest.toEntity());
    }

    public OrderDTO findOrderById(long id){
        var findOne = orderRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return findOne.toDTO();
    }

    public List<OrderListDTO> findAllOrders(){
        return orderRepository.findAll().stream().map(order -> order.toListDTO()).collect(Collectors.toList());
    }

}
