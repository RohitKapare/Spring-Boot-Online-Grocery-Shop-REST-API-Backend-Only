package com.rohit.grocery.groceryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.rohit.grocery.groceryapi.model.Customer;
import com.rohit.grocery.groceryapi.repository.CustomerRepository;

import com.rohit.grocery.groceryapi.exception.ResourceNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public List<Customer> getAllCustomers(){
    return customerRepository.findAll();
  }

  @Override
  public Customer getCustomerById(@NonNull Long id) {
    return customerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
  }

  @Override
  public Customer createCustomer(Customer customer) {
    // Check if customer with this email already exists
    if (customerRepository.existsByEmail(customer.getEmail())) {
      throw new IllegalArgumentException("Customer with email '" 
                + customer.getEmail() + "' already exists");
    }
    return customerRepository.save(customer);
  }

  @Override
  public Customer updateCustomer(Long id, Customer customerDetails) {
    // Validate input parameters
    if (id == null) {
      throw new IllegalArgumentException("Customer ID cannot be null");
    }
    if (customerDetails == null) {
      throw new IllegalArgumentException("Customer details cannot be null");
    }
    
    // Find the customer by id, throw exception if not found
    Customer existingCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    
    // Check if the new email is already taken by another customer
    if (!existingCustomer.getEmail().equals(customerDetails.getEmail()) && 
        customerRepository.existsByEmail(customerDetails.getEmail())) {
      throw new IllegalArgumentException("Customer with email '" 
                + customerDetails.getEmail() + "' already exists");
    }
    
    // Update name and email
    existingCustomer.setName(customerDetails.getName());
    existingCustomer.setEmail(customerDetails.getEmail());
    
    // Save and return the updated customer
    return customerRepository.save(existingCustomer);
  }

  @Override
  public void deleteCustomer(Long id) {
    // Validate input parameters
    if (id == null) {
      throw new IllegalArgumentException("Customer ID cannot be null");
    }
    
    // Check if customer exists before attempting to delete
    if (!customerRepository.existsById(id)) {
      throw new ResourceNotFoundException("Customer not found with id: " + id);
    }
    
    // Delete the customer by ID (more efficient than loading the entity first)
    customerRepository.deleteById(id);
  }

}
