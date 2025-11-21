package com.rohit.grocery.groceryapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.grocery.groceryapi.dto.OrderRequestDto;
import com.rohit.grocery.groceryapi.model.Order;
import com.rohit.grocery.groceryapi.service.OrderService;

@RestController
@RequestMapping("api/orders")
public class OrderController {
  
  @Autowired
  private OrderService orderService;

  //getAllOrders
  @GetMapping
  public List<Order> getAllOrders() {
    return orderService.getAllOrders();
  }

  //getOrderById
  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    Order order = orderService.getOrderById(id);
    return ResponseEntity.ok(order);
  }

  //getOrdderByCustomerId
  @GetMapping("/customer/{id}")
  public List<Order> getOrderByCustomerId(@PathVariable("id") Long id) {
    return orderService.getOrderByCustomerId(id);
  }

  //createOrder
  @PostMapping
  public Order createOrder(@RequestBody OrderRequestDto orderRequestDto) {
    return orderService.createOrder(orderRequestDto);
  }

  //deleteOrder
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.deleteOrder(id);
    return ResponseEntity.ok().build();
  }
}
