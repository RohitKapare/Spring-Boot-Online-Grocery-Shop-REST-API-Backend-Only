package com.rohit.grocery.groceryapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.grocery.groceryapi.model.Item;
import com.rohit.grocery.groceryapi.service.ItemService;

@RestController
@RequestMapping("api/items")
public class ItemController {
  
  @Autowired
  private ItemService itemService;

  //getAllItems
  @GetMapping
  public List<Item> getAllItems() {
    return itemService.getAllItems();
  }


  //getItemById
  @GetMapping("/{id}") 
  public ResponseEntity<Item> getItemById(@PathVariable Long id) {
    Item item = itemService.getItemById(id);
    return ResponseEntity.ok(item);
  }

  //increaseStock
  @PutMapping("/{id}/increase-stock")
  public ResponseEntity<String> increaseStock(@PathVariable Long id, @RequestParam int quantity) {
    itemService.increaseStock(id, quantity);
    return ResponseEntity.ok("Stock increased successfully");
  }

  //createItem
  @PostMapping
  public Item createItem(@RequestBody Item item) {
    return itemService.createItem(item);
  }

  //updateItem
  @PutMapping("/{id}")
  public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
    Item updatedItem = itemService.updateItem(id, itemDetails);
    return ResponseEntity.ok(updatedItem);
  }

  //deleteItem
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
    itemService.deleteItem(id);
    return ResponseEntity.ok().build();
  }
}
