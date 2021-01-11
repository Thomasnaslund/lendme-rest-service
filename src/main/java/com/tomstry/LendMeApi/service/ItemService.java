package com.tomstry.LendMeApi.service;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item addItem(@NonNull Item item) {
        //if contains id try update
        if (item.getId() < 0 || item.getId() != null)
            return updateItem(item);

            return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
         List items = itemRepository.findAll();
         return items;
    }

    public Item getItemByID(int id) {
     Item retrievedItem = itemRepository.findById(id).orElse(new Item());
     return retrievedItem;
    }

    public void deleteItem(int id) {
         itemRepository.deleteById(id);
    }

    public Item updateItem(Item itemToUpdate) {
        Item updatedItem = itemRepository.save(itemToUpdate);
        return updatedItem;
    }
}

