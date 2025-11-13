package com.rohit.grocery.groceryapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    private Long orderId;
    private Long groceryItemId;
    private String groceryItemName;
    private float groceryItemPrice;
    private int quantity;
    private float subtotal;
}