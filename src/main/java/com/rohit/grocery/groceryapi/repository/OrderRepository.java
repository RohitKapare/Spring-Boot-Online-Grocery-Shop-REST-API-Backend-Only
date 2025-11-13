package com.rohit.grocery.groceryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.grocery.groceryapi.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
  
}
