package com.rohit.grocery.groceryapi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private Long customerId;
    private List<OrderItemRequestDto> orderItems;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemRequestDto {
        private Long itemId;
        private int quantity;
    }
}