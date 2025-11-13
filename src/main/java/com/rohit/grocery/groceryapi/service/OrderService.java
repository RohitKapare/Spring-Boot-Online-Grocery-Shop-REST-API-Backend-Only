package com.rohit.grocery.groceryapi.service;

import java.util.List;

import com.rohit.grocery.groceryapi.dto.OrderRequestDto;
import com.rohit.grocery.groceryapi.model.Order;

public interface OrderService {
  List<Order> getAllOrders();
  Order getOrderById(Long id);
  List<Order> getOrderByCustomerId(Long id);
  Order createOrder(OrderRequestDto orderRequest);
  void deleteOrder(Long id);
}
