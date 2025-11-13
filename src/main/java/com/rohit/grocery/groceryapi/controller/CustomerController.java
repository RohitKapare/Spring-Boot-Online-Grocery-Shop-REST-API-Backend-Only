package com.rohit.grocery.groceryapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.grocery.groceryapi.model.Customer;
import com.rohit.grocery.groceryapi.service.CustomerService;


@RestController
@RequestMapping("api/customers")
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @GetMapping
  public List<Customer> getAllCustomers(){
    return customerService.getAllCustomers();
  }
  
}
