package com.rohit.grocery.groceryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.grocery.groceryapi.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
  
}
