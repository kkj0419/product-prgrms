package com.example.product_prgrms.controller;

import com.example.product_prgrms.exception.InvalidProductStockException;
import com.example.product_prgrms.exception.OrderInsertException;
import com.example.product_prgrms.model.order.OrderDTO;
import com.example.product_prgrms.model.order.OrderListDTO;
import com.example.product_prgrms.model.order.OrderRequest;
import com.example.product_prgrms.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RestOrderController {

    private final OrderService orderService;

    public RestOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<String> makeOrder(@RequestBody OrderRequest request) {
        try {
            orderService.createOrder(request);
        } catch (OrderInsertException e) {
            return new ResponseEntity<>(new String("주문 실패"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidProductStockException e) {
            return new ResponseEntity<>(new String("재고가 없습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new String("주문 생성"), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public List<OrderListDTO> getOrderList(){
        return orderService.findAllOrders();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable long id) {
        try {
            return new ResponseEntity<>(orderService.findOrderById(id), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
