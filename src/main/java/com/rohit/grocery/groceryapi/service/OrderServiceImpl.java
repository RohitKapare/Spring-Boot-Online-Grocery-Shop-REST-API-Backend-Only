package com.rohit.grocery.groceryapi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohit.grocery.groceryapi.dto.OrderRequestDto;
import com.rohit.grocery.groceryapi.exception.ResourceNotFoundException;
import com.rohit.grocery.groceryapi.model.Customer;
import com.rohit.grocery.groceryapi.model.Item;
import com.rohit.grocery.groceryapi.model.Order;
import com.rohit.grocery.groceryapi.model.OrderItem;
import com.rohit.grocery.groceryapi.repository.CustomerRepository;
import com.rohit.grocery.groceryapi.repository.ItemRepository;
import com.rohit.grocery.groceryapi.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private CustomerRepository  customerRepository;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemService itemService;
  
  @Override
  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }

  @Override
  public Order getOrderById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Order ID cannot be null");
    }
    return orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
  }

  @Override
  public List<Order> getOrderByCustomerId(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Customer ID cannot be null");
    }
    return orderRepository.findByCustomer_Id(id);
  }

  @Override
  public Order createOrder(OrderRequestDto orderRequestDto) {
    if (orderRequestDto == null) {
      throw new IllegalArgumentException("Order request cannot be null");
    }
    if (orderRequestDto.getCustomerId() == null) {
      throw new IllegalArgumentException("Customer ID cannot be null");
    }
    if (orderRequestDto.getOrderItems() == null || orderRequestDto.getOrderItems().isEmpty()) {
      throw new IllegalArgumentException("Order must contain at least one item");
    }

    Customer customer = customerRepository.findById(orderRequestDto.getCustomerId())
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: "
             + orderRequestDto.getCustomerId()));

    Order order = new Order();
    order.setCustomer(customer);
    order.setOrderDate(LocalDateTime.now());

    List<OrderItem> orderItems = new ArrayList<>();
    
    for (OrderRequestDto.OrderItemRequestDto itemRequest : orderRequestDto.getOrderItems()) {
      if (itemRequest.getItemId() == null) {
        throw new IllegalArgumentException("Item ID cannot be null");
      }
      if (itemRequest.getQuantity() <= 0) {
        throw new IllegalArgumentException("Quantity must be greater than 0");
      }

      Item item = itemRepository.findById(itemRequest.getItemId())
              .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " 
                + itemRequest.getItemId()));

      // Check stock availability
      if (item.getStockQuantity() < itemRequest.getQuantity()) {
        throw new IllegalArgumentException("Insufficient stock for item: " + item.getName() 
          + ". Available: " + item.getStockQuantity() 
          + ", Requested: " + itemRequest.getQuantity());
      }

      // Create order item
      OrderItem orderItem = new OrderItem();
      orderItem.setOrder(order);
      orderItem.setGroceryItem(item);
      orderItem.setQuantity(itemRequest.getQuantity());
      orderItem.setSubtotal(item.getPrice() * itemRequest.getQuantity());

      orderItems.add(orderItem);

      // Update stock quantity
      item.setStockQuantity(item.getStockQuantity() - itemRequest.getQuantity());
      itemRepository.save(item);
    }

    // Set order items
    order.setOrderItems(orderItems);

    // Save and return order
    return orderRepository.save(order);
  }

  @Override
  public void deleteOrder(Long id) {
    // Validate input
    if (id == null) {
      throw new IllegalArgumentException("Order ID cannot be null");
    }

    // Find the order to delete
    Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

    // Restore stock quantities for all items in the order
    for (OrderItem orderItem : order.getOrderItems()) {
        Item item = orderItem.getGroceryItem();
        Long itemId = item.getId();
        
        if (itemId != null) {
            try {
                itemService.increaseStock(itemId, orderItem.getQuantity());
            } catch (ResourceNotFoundException e) {
                // Item doesn't exist - create new item
                Item newItem = new Item();
                newItem.setName(item.getName());
                newItem.setCategory(item.getCategory());
                newItem.setPrice(item.getPrice());
                newItem.setStockQuantity(orderItem.getQuantity());
                itemService.createItem(newItem);
            }
        }
    }

    // Delete the order (this will cascade delete all order items due to orphanRemoval = true)
    orderRepository.delete(order);
  }
  
}
