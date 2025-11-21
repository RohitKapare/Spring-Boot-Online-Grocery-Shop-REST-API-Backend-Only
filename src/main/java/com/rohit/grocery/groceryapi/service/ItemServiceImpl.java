package com.rohit.grocery.groceryapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohit.grocery.groceryapi.exception.ResourceNotFoundException;
import com.rohit.grocery.groceryapi.model.Item;
import com.rohit.grocery.groceryapi.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService{

  @Autowired
  private ItemRepository itemRepository;

  @Override
  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

  @Override
  public Item getItemById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Item ID cannot be null");
    }
    return itemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
  }

  @Override
  public void increaseStock(Long itemId, int quantity) {
    Item existingItem = itemRepository.findById(itemId)
            .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemId));
    
    existingItem.setStockQuantity(existingItem.getStockQuantity() + quantity);
    itemRepository.save(existingItem);
  }

  @Override
  public Item createItem(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    return itemRepository.save(item);
  }

  @Override
  public Item updateItem(Long id, Item itemDetails) {
    if (id == null) {
      throw new IllegalArgumentException("Item ID cannot be null");
    }
    if (itemDetails == null) {
      throw new IllegalArgumentException("Item details cannot be null");
    }
    
    // Find the item by id
    Item existingItem = itemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
        
    // Update fields
    existingItem.setName(itemDetails.getName());
    if (itemDetails.getPrice() != null) {
      existingItem.setPrice(itemDetails.getPrice());
    }
    if (itemDetails.getStockQuantity() != existingItem.getStockQuantity()) {
      existingItem.setStockQuantity(itemDetails.getStockQuantity());
    }
    
    return itemRepository.save(existingItem);
  }

  @Override
  public void deleteItem(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Item ID cannot be null");
    }
    
    // Check if item exists before deleting
    if (!itemRepository.existsById(id)) {
      throw new ResourceNotFoundException("Item not found with id: " + id);
    }
    
    itemRepository.deleteById(id);
  }
  
}
