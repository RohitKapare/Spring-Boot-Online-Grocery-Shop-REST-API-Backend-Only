package com.rohit.grocery.groceryapi.service;

import java.util.List;

import com.rohit.grocery.groceryapi.model.Customer;

public interface CustomerService {
  List<Customer> getAllCustomers();
  Customer getCustomerById(Long id);
  Customer createCustomer(Customer customer);
  Customer updateCustomer(Long id, Customer customerDetails);
  void deleteCustomer(Long id);
}
