package com.rohit.grocery.groceryapi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grocery_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String name;
  
  @Column(nullable = false)
  private String category;
  
  @Column(nullable = false)
  private float price;
  
  @Column(name = "stock_quantity", nullable = false)
  private int stockQuantity;
  
  @OneToMany(mappedBy = "groceryItem", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();
}
