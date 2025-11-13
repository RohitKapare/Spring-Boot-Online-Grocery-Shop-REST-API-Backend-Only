package com.rohit.grocery.groceryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.grocery.groceryapi.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  boolean existsByEmail(String email);
}
