package com.rohit.grocery.groceryapi.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private LocalDateTime orderDate;
    private Long customerId;
    private String customerName;
    private List<OrderItemDto> orderItems;
    private float totalAmount;
}