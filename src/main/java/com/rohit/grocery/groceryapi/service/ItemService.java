package com.rohit.grocery.groceryapi.service;

import java.util.List;

import com.rohit.grocery.groceryapi.model.Item;

public interface ItemService {
  List<Item> getAllItems();
  Item getItemById(Long id);
  void increaseStock(Long id, int quantity);
  Item createItem(Item item);
  Item updateItem(Long id, Item itemDetails);
  void deleteItem(Long id);
}
